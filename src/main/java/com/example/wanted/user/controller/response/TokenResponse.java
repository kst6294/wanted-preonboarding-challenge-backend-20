package com.example.wanted.user.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
