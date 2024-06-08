package com.wanted.exception;

public class MemberNotFoundException extends IllegalArgumentException{

    public MemberNotFoundException(String message){
        super(message);
    }
}
