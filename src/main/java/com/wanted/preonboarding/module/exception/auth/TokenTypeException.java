package com.wanted.preonboarding.module.exception.auth;

import com.wanted.preonboarding.module.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class TokenTypeException extends ApplicationException {

    public static final String CODE = "TOKEN-400";
    public static final String MESSAGE = "잘못된 토큰 형식입니다.";

    public TokenTypeException() {
        super(CODE, HttpStatus.BAD_REQUEST, MESSAGE);
    }

}
