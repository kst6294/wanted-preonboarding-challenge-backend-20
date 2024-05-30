package com.wanted.market_api.exception;

import com.wanted.market_api.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
}
