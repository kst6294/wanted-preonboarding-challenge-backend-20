package com.wanted.market.global.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(){
        super("상품을 찾지 못했습니다.");
    }
}
