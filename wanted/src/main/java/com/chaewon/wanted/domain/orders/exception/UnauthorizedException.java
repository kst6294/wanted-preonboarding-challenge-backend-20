package com.chaewon.wanted.domain.orders.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException (String message) {
        super(message);
    }
}
