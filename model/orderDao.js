import dao_wrapper from "./basicDao.js";

const dao = dao_wrapper("Orders");

export default {
    ...dao,

    get_reserved_orders(buyer_id, sellers_item_ids) {
        return dao.get_orders({ status: "Reserved" }).andWhere(function () {
            this.where({ buyer_id }) // 구매자이거나
                .orWhereIn("product_id", sellers_item_ids); // 판매 상품이거나
        });
    },
};
