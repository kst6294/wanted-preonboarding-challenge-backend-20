package com.challenge.market.domain.member.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorResult {

    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "Can not find Member. ID : ");

    private final HttpStatus status;
    private final String message;

}
