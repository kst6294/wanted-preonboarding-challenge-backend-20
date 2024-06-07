package com.wantedmarket.global.exception.exception;

public abstract class BaseException extends RuntimeException {
    public abstract Enum<?> getErrorCode();

    public abstract String getDescription();
}
