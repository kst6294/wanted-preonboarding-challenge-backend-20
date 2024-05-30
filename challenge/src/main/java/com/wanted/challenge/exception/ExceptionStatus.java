package com.wanted.challenge.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionStatus {
    NEGATIVE_PRICE("금액은 음수일 수 없습니다."),
    NOT_FOUND("제공된 값을 찾을 수 없습니다."),
    SELF_BUY("본인 제품은 구매하실 수 없습니다.");

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
