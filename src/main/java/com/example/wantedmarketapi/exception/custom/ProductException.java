package com.example.wantedmarketapi.exception.custom;

import com.example.wantedmarketapi.exception.GlobalErrorCode;
import com.example.wantedmarketapi.exception.GlobalException;

public class ProductException extends GlobalException {

    public ProductException(GlobalErrorCode errorCode) {
        super(errorCode);
    }

}
