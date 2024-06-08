package com.api.jellomarket.enums.error

import org.springframework.http.HttpStatus

enum class ErrorCodeCustom(
    val code: String,
    val message: String,
    val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST
) {
    // 유저 관련 코드 : U-0001 ~ N
    INVALID_USER("U-0001", "등록되지 않은 회원정보 입니다."),
    INVALID_PASSWORD("U-0002", "비밀번호를 다시 입력해주세요"),
    NEED_SIGN_UP("U-0003", "회원가입 후 이용해주세요"),

    // 상품 관련 코드 : P-0001 ~ N
    INVALID_PRODUCT("P-0001", "존재하지 않는 상품입니다."),
    SOLD_OUT("P-0002", "품절된 상품입니다."),
    INVALID_PURCHASE("P-0003", "구매할 수 없는 상품입니다."),
    REQUIRED_ESSENTIAL_INFO("P-0004", "상품명, 가격, 재고가 제대로 입력되었는지 확인해주세요."),
    PRODUCT_PRICE_ZERO("P-0005", "상품 가격은 0원보다 많아야 합니다."),
    PRODUCT_STOCK_ZERO("P-0006", "상품 재고는 0개보다 많아야 합니다."),

    // 기타 코드 : E-0001 ~ N
    CALL_ADMIN("E-0001", "관리자에게 문의해주세요."),
    REQUIRED_FIELD("E-0002", "필수 정보가 누락되었습니다."),


}