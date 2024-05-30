package com.sunyesle.wanted_market.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    public String name();

    public HttpStatus getHttpStatus();

    public String getMessage();
}
