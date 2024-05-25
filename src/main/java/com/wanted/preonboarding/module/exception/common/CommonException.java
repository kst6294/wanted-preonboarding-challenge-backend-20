package com.wanted.preonboarding.module.exception.common;

import com.wanted.preonboarding.module.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class CommonException extends ApplicationException {

    protected CommonException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
