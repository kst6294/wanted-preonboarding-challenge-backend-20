package wanted.market.common

enum class ErrorCode(val httpStatus: Int, val message: String) {

    OK(200, "Operation successful"),

    MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
    DUPLICATE_EMAIL(500, "중복되는 이메일입니다."),

    PRODUCT_NOT_FOUND(404, "존재하지 않는 상품입니다."),
    PRODUCT_NOT_FOR_SALE(400, "판매 중인 상품이 아닙니다."),


    ORDER_NOT_FOUND(404, "존재하지 않는 주문입니다."),
}