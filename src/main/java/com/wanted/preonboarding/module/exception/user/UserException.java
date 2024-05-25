package com.wanted.preonboarding.module.exception.user;

import com.wanted.preonboarding.module.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserException extends ApplicationException {

    protected UserException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
