package com.challenge.market.domain.member.exception;

import com.challenge.market.domain.member.constants.MemberErrorResult;
import lombok.Getter;


@Getter
public class MemberNotFoundException extends RuntimeException{

        MemberErrorResult error;
        private final String userName;

    public MemberNotFoundException(MemberErrorResult error, String userName) {
        super(error.getMessage() + userName);
        this.error = error;
        this.userName = userName;
    }




}
