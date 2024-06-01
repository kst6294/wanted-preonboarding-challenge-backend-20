package com.chaewon.wanted.domain.orders.exception;

public class SelfPurchaseException extends RuntimeException {
    public SelfPurchaseException(String message) {
        super(message);
    }
}
