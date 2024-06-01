package com.wanted.preonboarding.global.exception.errorCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum PurchaseErrorCode implements ErrorCode {
    QUANTITY_IS_ZERO(HttpStatus.BAD_REQUEST, "C001", "상품 재고가 0입니다."),
    ALREADY_PURCHASED_PRODUCT(HttpStatus.BAD_REQUEST, "C002", "이미 구매한 상품입니다."),
    ALREADY_CONFIRMED_PURCHASE(HttpStatus.BAD_REQUEST, "C003", "이미 구매확정한 주문입니다."),
    ALREADY_APPROVED_PURCHASE(HttpStatus.BAD_REQUEST, "C004", "이미 판매승인한 주문입니다."),
    PRODUCT_SELLER_SAME_CUSTOMER(HttpStatus.BAD_REQUEST, "C005", "상품 판매자와 구매자가 동일합니다."),
    PRODUCT_STATE_IS_NOT_SALE(HttpStatus.BAD_REQUEST, "C006", "상품이 구매 가능한 상태가 아닙니다."),
    ;

    private HttpStatus httpStatus;
    private String code;
    private String message;

    PurchaseErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}