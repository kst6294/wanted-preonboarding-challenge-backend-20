package com.chaewon.wanted.domain.orders.exception;

public class NoOrderException extends RuntimeException {
    public NoOrderException (String message) {
        super(message);
    }
}
