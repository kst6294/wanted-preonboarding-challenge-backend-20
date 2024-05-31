package com.chaewon.wanted.base.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long accessTokenExpiresIn; // accessToken 만료 시간 추가
}