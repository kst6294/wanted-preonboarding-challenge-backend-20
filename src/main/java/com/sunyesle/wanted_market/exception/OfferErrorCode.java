package com.sunyesle.wanted_market.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum OfferErrorCode implements ErrorCode {
    OFFER_NOT_FOUND(HttpStatus.NOT_FOUND, "요청이 존재하지 않습니다."),
    NOT_OFFER_OFFEROR(HttpStatus.FORBIDDEN, "요청 제안자가 아닙니다."),
    NOT_OFFER_OFFEREE(HttpStatus.FORBIDDEN, "요청 수신자가 아닙니다."),
    DUPLICATE_OFFER(HttpStatus.BAD_REQUEST, "중복된 요청이 존재합니다."),
    INVALID_OFFER_STATUS(HttpStatus.BAD_REQUEST, "요청의 상태가 유효하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    OfferErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
