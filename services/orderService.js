import { transaction } from "../db/dbClient.js";
import orderDao from "../model/orderDao.js";
import productDao from "../model/productDao.js";
const { get_orders, get_order, update_order } = orderDao;
const { get_product } = productDao;

export async function show_orders({ buyer_id, product_id, seller_id }) {
    const is_sellers_product = await get_product({ product_id, seller_id });

    if (is_sellers_product) {
        // 판매자로서 주문서
        return get_orders({ product_id });
    } else {
        // 구매자로서 주문서
        return get_order({ buyer_id, product_id });
    }
}

export async function approve_sale_order({ buyer_id, product_id, seller_id }) {
    let updated_order;

    await transaction(async (trx) => {
        const is_sellers_product = await get_product({ product_id, seller_id }).transacting(trx);

        if (is_sellers_product) {
            const item = await get_order({ buyer_id, product_id, status: "Reserved" }).transacting(trx);

            if (item) {
                await update_order({ buyer_id, product_id, status: "Reserved" }, { status: "Approval" }).transacting(trx);

                updated_order = await get_order({ buyer_id, product_id }).transacting(trx);
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

export async function confirm_purchase_order({ buyer_id, product_id }) {
    let updated_order;

    await transaction(async (trx) => {
        const item = await get_order({ buyer_id, product_id, status: "Approval" }).transacting(trx);

        if (item) {
            await update_order({ buyer_id, product_id, status: "Approval" }, { status: "Confirm" }).transacting(trx);

            updated_order = await get_order({ buyer_id, product_id }).transacting(trx);
        } else {
            const error = new Error("판매 승인 상품 아님");
            error.status = 409;
            throw error;
        }
    });

    return updated_order;
}
