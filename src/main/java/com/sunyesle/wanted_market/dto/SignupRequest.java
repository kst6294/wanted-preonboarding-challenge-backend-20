package com.sunyesle.wanted_market.dto;

import lombok.Getter;

@Getter
public class SignupRequest {
    private final String name;
    private final String email;
    private final String password;

    public SignupRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
