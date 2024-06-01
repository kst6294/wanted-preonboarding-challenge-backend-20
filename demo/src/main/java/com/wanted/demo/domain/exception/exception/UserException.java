package com.wanted.demo.domain.exception.exception;

import com.wanted.demo.domain.exception.responseCode.UserExceptionResponseCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException{
    private UserExceptionResponseCode exception;

    private String log;

    public UserException(UserExceptionResponseCode exception, String log){
        this.exception = exception;
        this.log = log;
    }
}
