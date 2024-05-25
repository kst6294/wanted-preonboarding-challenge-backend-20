package com.wanted.preonboarding.module.exception.common;

import org.springframework.http.HttpStatus;

public class JsonSerializationException extends CommonException{

    public static final String CODE = "JSON-500";
    public static final String MESSAGE = "제이슨 직렬화 도중 에러가 발생했습니다.";

    public JsonSerializationException(String message) {
        super(CODE, HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE + message);
    }
}
