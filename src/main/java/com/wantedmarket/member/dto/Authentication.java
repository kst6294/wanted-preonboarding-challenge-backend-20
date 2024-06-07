package com.wantedmarket.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Authentication {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String username;
        String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String token;
    }
}
