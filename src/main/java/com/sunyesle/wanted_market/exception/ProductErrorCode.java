package com.sunyesle.wanted_market.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ProductErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "제품이 존재하지 않습니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "제품의 재고가 부족합니다."),
    NOT_PRODUCT_OWNER(HttpStatus.FORBIDDEN, "상품 접근 권한이 없습니다."),
    INVALID_PRODUCT_STATUS(HttpStatus.BAD_REQUEST, "제품의 상태가 유효하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ProductErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
