package com.wanted.preonboarding.module.exception.order;

import org.springframework.http.HttpStatus;

public class InvalidOrderException extends OrderException{

    public static final String CODE = "ORDER-400";
    public static final String MESSAGE = "자신이 판매하는 물건을 자신이 구매할 수 없습니다.";

    public InvalidOrderException() {
        super(CODE, HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
