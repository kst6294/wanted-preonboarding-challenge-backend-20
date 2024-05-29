package com.example.wanted.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLogin {
    private final String account;
    private final String password;

    @Builder
    public UserLogin(
            @JsonProperty("account") String account,
            @JsonProperty("password") String password
    ) {
        this.account = account;
        this.password = password;
    }
}
