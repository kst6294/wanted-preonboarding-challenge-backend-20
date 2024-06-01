package com.wanted.challenge.domain.exception.exception;

import com.wanted.challenge.domain.exception.info.MemberExceptionInfo;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{

    private MemberExceptionInfo exception;

    private String log;

    public MemberException(MemberExceptionInfo exception, String log){
        this.exception = exception;
        this.log = log;
    }
}
