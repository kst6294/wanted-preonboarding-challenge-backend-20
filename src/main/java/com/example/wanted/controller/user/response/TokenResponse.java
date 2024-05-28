package com.example.wanted.controller.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {
    private final String access;

    @Builder
    public TokenResponse(String access) {
        this.access = access;
    }

    public static TokenResponse from(String access) {
        return TokenResponse.builder()
                .access(access)
                .build();
    }
}
