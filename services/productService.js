import { transaction } from "../db/dbClient.js";
import productDao from "../model/productDao.js";
import orderDao from "../model/orderDao.js";

const { get_all_products, get_product, insert_product, decrease_product } = productDao;
const { get_order, insert_order } = orderDao;

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
