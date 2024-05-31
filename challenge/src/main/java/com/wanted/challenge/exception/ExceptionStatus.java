package com.wanted.challenge.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionStatus {
    NEGATIVE_PRICE("금액은 음수 혹은 0일 수 없습니다."),
    NOT_FOUND("제공된 값을 찾을 수 없습니다."),
    SELF_BUY("본인 제품은 구매하실 수 없습니다."),
    NOT_SELLER("판매자가 아닙니다."),
    CAN_NOT_APPROVE("판매를 승인할 수 없습니다. 구매 정보가 없거나 이미 승인되었습니다."),
    NEGATIVE_QUANTITY("수량은 음수일 수 없습니다."),
    CAN_NOT_PURCHASE("구매할 수 없습니다."),
    PURCHASE_ALREADY("이미 구매한 상품입니다.");

    private final String message;
    private final HttpStatus httpStatus;

    ExceptionStatus(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    ExceptionStatus(String message) {
        this(message, BAD_REQUEST);
    }
}
