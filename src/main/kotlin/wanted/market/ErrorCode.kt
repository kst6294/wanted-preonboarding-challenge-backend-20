package wanted.market

enum class ErrorCode(val httpStatus: Int, val message: String) {
    MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
    DUPLICATE_EMAIL(500, "중복되는 이메일입니다."),

    PRODUCT_NOT_FOUND(404, "존재하지 않는 상품입니다.")
}