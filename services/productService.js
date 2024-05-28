import { transaction } from "../db/dbClient.js";
import productDao from "../model/productDao.js";
import orderDao from "../model/orderDao.js";

const { get_all_products, get_product, insert_product, decrease_product } = productDao;
const { get_order, update_order, insert_order } = orderDao;

export function view_product_list() {
    return get_all_products();
}

export function view_product_detail(product_id) {
    return get_product({ product_id });
}

export async function register_product(seller_id, post_data) {
    const [product_id] = await insert_product({
        status: "Available",
        seller_id,
        ...post_data,
    });

    return product_id;
}

export async function purchase_product(product_id, buyer_id) {
    let order;

    await transaction(async (trx) => {
        try {
            const item = await get_product({ product_id, status: "Available" }).transacting(trx);

            if (item) {
                // 주문서 작성
                await insert_order({
                    buyer_id,
                    product_id,
                    price: item.price,
                    status: "Reserved",
                }).transacting(trx);
                await decrease_product({ product_id }).transacting(trx);

                // 주문서 확인
                order = await get_order({ buyer_id, product_id }).transacting(trx);

                await trx.commit();
            } else {
                const error = new Error("판매 중이지 않음");
                error.status = 409;

                throw error;
            }
        } catch (error) {
            await trx.rollback();

            throw error;
        }
    });
    return order;
}

export async function approve_sale_product(product_id, buyer_id, seller_id) {
    let updated_order;

    await transaction(async (trx) => {
        const is_sellers_proudct = await get_product({ product_id, seller_id }).transacting(trx);

        if (is_sellers_proudct) {
            const item = await get_order({ product_id, buyer_id, status: "Reserved" }).transacting(trx);

            if (item) {
                await update_order({ product_id, buyer_id, status: "Reserved" }, { status: "Approval" }).transacting(trx);

                updated_order = await get_order({ product_id, buyer_id }).transacting(trx);
            } else {
                const error = new Error("예약 상품 아님");
                error.status = 409;
                throw error;
            }
        } else {
            const error = new Error("판매자 상품이 아님");
            error.status = 409;
            throw error;
        }
    });

    return updated_order;
}

export async function confirm_purchase_product(product_id, buyer_id) {
    let updated_order;

    await transaction(async (trx) => {
        const item = await get_order({ product_id, buyer_id, status: "Approval" }).transacting(trx);

        if (item) {
            await update_order({ product_id, buyer_id, status: "Approval" }, { status: "Confirm" }).transacting(trx);

            updated_order = await get_order({ product_id, buyer_id }).transacting(trx);
        } else {
            const error = new Error("판매 승인 상품 아님");
            error.status = 409;
            throw error;
        }
    });

    return updated_order;
}
