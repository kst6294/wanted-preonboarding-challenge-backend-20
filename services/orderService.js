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

export async function approve_sale_order({ buyer_id, product_id, seller_id }, transactionProvider) {
    const transaction = await transactionProvider();

    try {
        let updated_order;
        const is_sellers_product = await get_product({ product_id, seller_id }).transacting(transaction);

        if (is_sellers_product) {
            const item = await get_order({ buyer_id, product_id, status: "Reserved" }).transacting(transaction);

            if (item) {
                await update_order({ buyer_id, product_id, status: "Reserved" }, { status: "Approval" }).transacting(transaction);

                updated_order = await get_order({ buyer_id, product_id }).transacting(transaction);
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

        await transaction.commit();
        return updated_order;
    } catch (error) {
        await transaction.rollback();
        throw error;
    }
}

export async function confirm_purchase_order({ buyer_id, product_id }, transactionProvider) {
    const transaction = await transactionProvider();

    try {
        let updated_order;
        const item = await get_order({ buyer_id, product_id, status: "Approval" }).transacting(transaction);

        if (item) {
            await update_order({ buyer_id, product_id, status: "Approval" }, { status: "Confirm" }).transacting(transaction);

            updated_order = await get_order({ buyer_id, product_id }).transacting(transaction);
        } else {
            const error = new Error("판매 승인 상품 아님");
            error.status = 409;
            throw error;
        }

        await transaction.commit();
        return updated_order;
    } catch (error) {
        await transaction.rollback();
        throw error;
    }
}
