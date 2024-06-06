package com.market.wanted.member.dto;

import lombok.Data;

@Data
public class SignupResponse {

    private String message;

    public SignupResponse(String message) {
        this.message = message;
    }
}
