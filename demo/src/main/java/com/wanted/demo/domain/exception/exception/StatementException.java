package com.wanted.demo.domain.exception.exception;

import com.wanted.demo.domain.exception.responseCode.ProductExceptionResponseCode;
import com.wanted.demo.domain.exception.responseCode.StatementsExceptionResponseCode;
import lombok.Getter;

@Getter
public class StatementException extends RuntimeException{

    private StatementsExceptionResponseCode exception;

    private String log;

    public StatementException(StatementsExceptionResponseCode exception, String log){
        this.exception = exception;
        this.log = log;
    }
}
