package com.wanted.exception;

public class InvalidCredentialsException extends IllegalArgumentException{
    public InvalidCredentialsException(String message){
        super(message);
    }
}
