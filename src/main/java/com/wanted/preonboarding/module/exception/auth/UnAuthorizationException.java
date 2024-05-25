package com.wanted.preonboarding.module.exception.auth;

import com.wanted.preonboarding.module.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UnAuthorizationException extends ApplicationException {

    public static final String CODE = "AUTH-401";

    public UnAuthorizationException(String message) {
        super(CODE, HttpStatus.UNAUTHORIZED, message);
    }
}
