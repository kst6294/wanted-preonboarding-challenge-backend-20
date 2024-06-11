package org.example.wantedmarket.error;

import lombok.*;
@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String errorMessage;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getErrorMessage();
    }

}
