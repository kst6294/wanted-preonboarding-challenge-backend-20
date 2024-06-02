package com.chaewon.wanted.domain.orders.exception;

public class NotEnoughQuantityException extends RuntimeException {
    public NotEnoughQuantityException(String message) {
        super(message);
    }
}
