package com.sunyesle.wanted_market.dto;

import lombok.Getter;

@Getter
public class SigninRequest {
    private final String email;
    private final String password;

    public SigninRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
