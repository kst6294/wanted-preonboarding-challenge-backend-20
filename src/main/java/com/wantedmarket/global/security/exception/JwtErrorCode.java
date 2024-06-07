package com.wantedmarket.global.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtErrorCode {
    EMPTY_TOKEN("헤더에 토큰이 없습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED("토큰이 만료되었습니다.");
    private final String description;
}
