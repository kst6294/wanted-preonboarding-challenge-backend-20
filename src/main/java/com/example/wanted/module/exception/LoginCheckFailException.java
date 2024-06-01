package com.example.wanted.module.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginCheckFailException extends AuthenticationException {
    public LoginCheckFailException() {
        super("아이디 또는 비밀번호를 다시 확인해주세요.");
    }
}