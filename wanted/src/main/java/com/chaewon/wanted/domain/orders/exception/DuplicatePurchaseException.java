package com.chaewon.wanted.domain.orders.exception;

public class DuplicatePurchaseException extends RuntimeException {
    public DuplicatePurchaseException(String message) {
        super(message);
    }
}
