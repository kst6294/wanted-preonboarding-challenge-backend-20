package com.example.wanted.module.exception;

public class OrderConfirmationNotAllowedException extends RuntimeException{
    public OrderConfirmationNotAllowedException(String message) {
        super(message);
    }
}
