package com.chaewon.wanted.domain.product.exception;

public class NoProductsForSaleException extends RuntimeException {
    public NoProductsForSaleException (String message) {
        super(message);
    }
}
