package com.wanted.preonboarding.module.exception.product;

import org.springframework.http.HttpStatus;

public class NotFoundProductException extends ProductException{

    public static final String CODE = "PRODUCT-404";
    public static final String MESSAGE = "해당 상품을 찾을 수 없습니다.";

    public NotFoundProductException(long productId) {
        super(CODE, HttpStatus.NOT_FOUND, buildMessage(productId));
    }


    private static String buildMessage(long productId){
        return String.format("%s 상품 ID: %d", MESSAGE, productId);
    }

}
