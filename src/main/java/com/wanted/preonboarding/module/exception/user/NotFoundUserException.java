package com.wanted.preonboarding.module.exception.user;

import org.springframework.http.HttpStatus;

public class NotFoundUserException extends UserException{

    public static final String CODE = "USER-404";
    public static final String MESSAGE = "해당 유저를 찾을 수 없습니다.";

    public NotFoundUserException(String phoneNumber) {
        super(CODE, HttpStatus.NOT_FOUND, buildMessage(phoneNumber));
    }


    private static String buildMessage(String phoneNumber){
        return String.format(MESSAGE +"%s", phoneNumber);
    }
}
