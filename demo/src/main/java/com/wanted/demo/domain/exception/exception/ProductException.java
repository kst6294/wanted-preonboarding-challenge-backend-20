package com.wanted.demo.domain.exception.exception;

import com.wanted.demo.domain.exception.responseCode.ProductExceptionResponseCode;
import com.wanted.demo.domain.exception.responseCode.UserExceptionResponseCode;
import lombok.Getter;

@Getter
public class ProductException extends RuntimeException{
    private ProductExceptionResponseCode exception;

    private String log;

    public ProductException(ProductExceptionResponseCode exception, String log){
        this.exception = exception;
        this.log = log;
    }
}
