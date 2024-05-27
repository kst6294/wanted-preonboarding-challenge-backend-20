package com.wanted.preonboarding.module.exception.common;

import com.wanted.preonboarding.module.exception.ApplicationException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ProviderMappingException extends ApplicationException {

    public static final String CODE = "PROVIDER_PATTERN-400";
    public static final String MESSAGE = "해당 프로바이더를 찾을 수 없습니다.";
    public static final org.springframework.http.HttpStatus HttpStatus = INTERNAL_SERVER_ERROR;

    public ProviderMappingException(String key) {
        super(CODE, HttpStatus, buildMessage(key));
    }

    private static String buildMessage(String key){
        return String.format("%s 프로바이더 KEY: %s", MESSAGE, key);
    }
}

