package com.wanted.preonboarding.module.exception.product;

import org.springframework.http.HttpStatus;

public class ProductOutOfStockException extends ProductException {

    public static final String CODE = "PRODUCT-400";
    public static final String MESSAGE = "해당 상품은 품절입니다.";

    public ProductOutOfStockException(long productId) {
        super(CODE, HttpStatus.BAD_REQUEST, buildMessage(productId));
    }


    private static String buildMessage(long productId){
        return String.format("%s 상품 ID: %d", MESSAGE, productId);
    }

}
