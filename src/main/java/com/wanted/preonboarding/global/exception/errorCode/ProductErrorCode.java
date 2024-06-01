package com.wanted.preonboarding.global.exception.errorCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ProductErrorCode implements ErrorCode {
    Product_NOT_FOUND(HttpStatus.BAD_REQUEST, "P001", "상품이 존재하지 않습니다")

    ;

    private HttpStatus httpStatus;
    private String code;
    private String message;

    ProductErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}