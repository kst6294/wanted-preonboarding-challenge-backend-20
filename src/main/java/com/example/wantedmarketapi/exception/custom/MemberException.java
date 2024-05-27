package com.example.wantedmarketapi.exception.custom;

import com.example.wantedmarketapi.exception.GlobalErrorCode;
import com.example.wantedmarketapi.exception.GlobalException;

public class MemberException extends GlobalException {

    public MemberException(GlobalErrorCode errorCode) {
        super(errorCode);
    }
}
