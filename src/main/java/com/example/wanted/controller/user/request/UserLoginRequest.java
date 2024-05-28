package com.example.wanted.controller.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginRequest {
    private final String account;
    private final String password;

    @Builder
    public UserLoginRequest(
            @JsonProperty("account") String account,
            @JsonProperty("password") String password
    ) {
        this.account = account;
        this.password = password;
    }
}
