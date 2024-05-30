package com.exception_study.user_account.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String userId;
    private String password;
}
