package com.example.wantedmarketapi.exception.custom;

import com.example.wantedmarketapi.exception.GlobalErrorCode;
import com.example.wantedmarketapi.exception.GlobalException;

public class AuthException extends GlobalException {

    public AuthException(GlobalErrorCode errorCode) {
        super(errorCode);
    }
}