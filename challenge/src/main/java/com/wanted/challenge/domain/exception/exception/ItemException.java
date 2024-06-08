package com.wanted.challenge.domain.exception.exception;

import com.wanted.challenge.domain.exception.info.ItemExceptionInfo;
import lombok.Getter;

@Getter
public class ItemException extends RuntimeException{

    private ItemExceptionInfo exception;

    private String log;

    public ItemException(ItemExceptionInfo exception, String log){
        this.exception = exception;
        this.log = log;
    }
}
