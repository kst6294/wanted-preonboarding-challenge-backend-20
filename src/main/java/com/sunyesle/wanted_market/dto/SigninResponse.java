package com.sunyesle.wanted_market.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class SigninResponse {
    private final String token;

    @JsonCreator
    public SigninResponse(String token) {
        this.token = token;
    }
}
