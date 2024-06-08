package com.chaewon.wanted.domain.product.exception;

public class DuplicatePriceException extends RuntimeException {
    public DuplicatePriceException(String message) {
        super(message);
    }
}
