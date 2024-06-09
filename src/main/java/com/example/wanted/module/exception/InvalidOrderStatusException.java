package com.example.wanted.module.exception;

public class InvalidOrderStatusException extends RuntimeException{
    public InvalidOrderStatusException(String message) {
        super(message);
    }
}
