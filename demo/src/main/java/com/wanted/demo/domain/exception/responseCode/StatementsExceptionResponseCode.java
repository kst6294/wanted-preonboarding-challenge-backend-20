package com.wanted.demo.domain.exception.responseCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatementsExceptionResponseCode {
    //409 CONFLICT
    NOT_MATCH_BUYER(HttpStatus.CONFLICT, "S-002", "판매자가 아닙니다."),
    ALREADY_SALE_PRODUCT_CONFIRM(HttpStatus.CONFLICT, "S-003", "이미 구매 확정한 상품입니다."),

    //404 NOT FOUND
    STATEMENTS_NOT_FOUND(HttpStatus.NOT_FOUND,"S-001", "기록이 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    StatementsExceptionResponseCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
