package com.wanted.challenge.domain.exception.exception;

import com.wanted.challenge.domain.exception.info.TransactionHistoryExceptionInfo;
import lombok.Getter;

@Getter
public class TransactionHistoryException extends RuntimeException{

    private TransactionHistoryExceptionInfo exception;

    private String log;

    public TransactionHistoryException(TransactionHistoryExceptionInfo exception, String log){
        this.exception = exception;
        this.log = log;
    }
}
