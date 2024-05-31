package com.exception_study.api.user_account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private String userId;
    private String password;

    public static LoginRequest of(String userId, String password){
        return new LoginRequest(userId,password);
    }
}
