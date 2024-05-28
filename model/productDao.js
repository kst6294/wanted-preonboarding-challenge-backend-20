import dao_wrapper from "./basicDao.js";

const dao = dao_wrapper("Products");

export default {
    ...dao,

    decrease_product: (dto) => dao.get_product(dto).decrement("amount", 1),
};
