package com.wanted.preonboarding.global.exception.entity;

import com.wanted.preonboarding.global.exception.errorCode.ErrorCode;
import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {
    private ErrorCode errorCode;
    private String log;

    public RestApiException(ErrorCode errorCode, String log) {
        this.errorCode = errorCode;
        this.log = log;
    }
}
