package com.chaewon.wanted.domain.orders.exception;

public class OrderStatusNotApprovedException extends RuntimeException {
    public OrderStatusNotApprovedException(String message) {
        super(message);
    }
}
