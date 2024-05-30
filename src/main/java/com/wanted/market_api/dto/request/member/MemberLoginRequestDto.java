package com.wanted.market_api.dto.request.member;

import lombok.Getter;

@Getter
public class MemberLoginRequestDto {
    private String username;
    private String password;
}
