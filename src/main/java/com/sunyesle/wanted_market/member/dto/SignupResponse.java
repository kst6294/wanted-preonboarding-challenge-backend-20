package com.sunyesle.wanted_market.member.dto;

import lombok.Getter;

@Getter
public class SignupResponse {
    private final Long id;
    private final String name;
    private final String email;

    public SignupResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
