package com.wanted.market.global.exception;

import lombok.Getter;

@Getter
public class InvalidProductStatusException extends RuntimeException{
    public InvalidProductStatusException(){
        super("잘못된 상품 상태입니다.");
    }
}
