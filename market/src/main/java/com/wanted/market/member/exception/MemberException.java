package com.wanted.market.member.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    private MemberErrorCode memberErrorCode;

    public MemberException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
    }
}
