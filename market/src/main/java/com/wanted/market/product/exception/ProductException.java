package com.wanted.market.product.exception;



public class ProductException extends RuntimeException {

    private ProductErrorCode productErrorCode;

    public ProductException(ProductErrorCode productErrorCode) {
    }
}
