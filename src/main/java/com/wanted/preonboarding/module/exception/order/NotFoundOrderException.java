package com.wanted.preonboarding.module.exception.order;

import org.springframework.http.HttpStatus;

public class NotFoundOrderException extends OrderException {

    public static final String CODE = "ORDER-404";
    public static final String MESSAGE = "해당 주문을 찾을 수 없습니다.";

    public NotFoundOrderException(long orderId) {
        super(CODE, HttpStatus.NOT_FOUND, buildMessage(orderId));
    }

    private static String buildMessage(long orderId){
        return String.format("%s 주문 ID: %d", MESSAGE, orderId);
    }
}
