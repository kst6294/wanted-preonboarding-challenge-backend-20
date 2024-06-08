package com.wantedmarket.Deal.exception;

import com.wantedmarket.global.exception.exception.BaseException;
import lombok.Getter;

@Getter
public class DealException extends BaseException {
    private final DealErrorCode errorCode;
    private final String description;

    public DealException(DealErrorCode errorCode) {
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }
}
