package com.chaewon.wanted.domain.orders.exception;

public class OrderAlreadyConfirmedException extends RuntimeException {
    public OrderAlreadyConfirmedException(String message) {
        super(message);
    }
}
