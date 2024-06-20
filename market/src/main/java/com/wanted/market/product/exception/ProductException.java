package com.wanted.market.product.exception;


import lombok.Getter;

@Getter
public class ProductException extends RuntimeException {

    private final ProductErrorCode productErrorCode;

    public ProductException(ProductErrorCode productErrorCode) {
        super(productErrorCode.getMessage());
        this.productErrorCode = productErrorCode;
    }

}
