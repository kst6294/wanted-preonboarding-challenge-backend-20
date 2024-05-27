package com.wanted.preonboarding.module.exception.order;

import com.wanted.preonboarding.module.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class OrderException extends ApplicationException {

    protected OrderException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
