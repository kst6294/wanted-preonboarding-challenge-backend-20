package com.wanted.preonboarding.global.exception.errorCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
