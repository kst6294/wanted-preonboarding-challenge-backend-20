package com.wanted.demo.domain.exception.responseCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ProductExceptionResponseCode {

    //403 forbidden
    PRODUCT_NOT_ENOUGH_QUANTITY(HttpStatus.FORBIDDEN,"P-002","상품의 재고가 부족합니다."),
    FORBID_YOURSELF_BUYING_PRODUCT(HttpStatus.FORBIDDEN,"P-003","자신의 상품을 구매할 수 없습니다."),
    ALREADY_PURCHASE_PRODUCT(HttpStatus.FORBIDDEN,"P-004","이미 구매한 상품입니다."),

    //404
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND,"P-001", "상품이 존재하지 않습니다.");




    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    ProductExceptionResponseCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
