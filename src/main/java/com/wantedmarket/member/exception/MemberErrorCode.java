package com.wantedmarket.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {
    NO_MEMBER("사용자가 없습니다."),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다."),
    ALREADY_EXISTS("이미 존재하는 사용자입니다.");
    private final String description;
}
