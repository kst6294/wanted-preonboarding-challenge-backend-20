package wanted.market.common.exception

enum class ErrorCode(val httpStatus: Int, val message: String) {

    OK(200, "Operation successful"),

    MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
    DUPLICATE_EMAIL(500, "중복되는 이메일입니다."),

    PRODUCT_NOT_FOUND(404, "존재하지 않는 상품입니다."),
    PRODUCT_NOT_FOR_SALE(400, "판매 중인 상품이 아닙니다."),
    SELLER_APPROVAL_ONLY(400, "판매자만 승인할 수 있습니다."),


    ORDER_NOT_FOUND(404, "존재하지 않는 주문입니다."),

    BAD_REQUEST(400, "클라리언트의 잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 에러입니다.")
}