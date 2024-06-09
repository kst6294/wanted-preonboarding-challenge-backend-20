package com.example.wanted.module.exception;

public class SellerCannotMakeOrderException extends RuntimeException{
    public SellerCannotMakeOrderException(String message) {
        super(message);
    }
}
