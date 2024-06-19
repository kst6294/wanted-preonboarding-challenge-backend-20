package com.wanted.market.order.exception;

import com.wanted.market.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum OrderErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "주문내역을 찾을 수 없습니다.");

    private HttpStatus httpStatus;
    private String message;

    OrderErrorCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
