package com.chaewon.wanted.domain.member.exception;

public class MultipleLoginException extends RuntimeException {
    public MultipleLoginException(String msg) {
        super(msg);
    }
}