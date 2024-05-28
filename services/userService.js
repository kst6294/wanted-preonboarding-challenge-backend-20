import { transaction } from "../db/dbClient.js";
import orderDao from "../model/orderDao.js";
import productDao from "../model/productDao.js";

const { get_orders, get_reserved_orders } = orderDao;
const { get_products } = productDao;

export function show_purchased_orders(buyer_id) {
    return get_orders({
        status: "Confirm",
        buyer_id,
    });
}

export async function show_reserved_orders(buyer_id, seller_id) {
    let reserved_orders;

    await transaction(async (trx) => {
        // 판매자로서 상품 모음
        const items = await get_products({ seller_id }).transacting(trx);
        const sellers_item_ids = items.map((item) => item.product_id);

        reserved_orders = await get_reserved_orders(buyer_id, sellers_item_ids).transacting(trx);
    });
    return reserved_orders;
}
