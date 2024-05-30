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

export async function register_product({ seller_id, post_data }) {
    const [product_id] = await insert_product({
        status: "Available",
        seller_id,
        ...post_data,
    });

    return product_id;
}

export async function purchase_product({ product_id, buyer_id }, transactionProvider) {
    const transaction = await transactionProvider();

    try {
        let order;
        const item = await get_product({ product_id, status: "Available" }).transacting(transaction);

        if (item) {
            // 주문서 작성
            await insert_order({
                buyer_id,
                product_id,
                price: item.price,
                status: "Reserved",
            }).transacting(transaction);

            await decrease_product({ product_id }).transacting(transaction);

            // 주문서 확인
            order = await get_order({ buyer_id, product_id }).transacting(transaction);
        } else {
            const error = new Error("판매 중이지 않음");
            error.status = 409;

            throw error;
        }

        await transaction.commit();
        return order;
    } catch (error) {
        await transaction.rollback();
        throw error;
    }
}
