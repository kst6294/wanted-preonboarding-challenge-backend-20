package com.example.wantedmarketapi.common.exception;

public class InvalidParamException extends WantedMarketApiException {

    public InvalidParamException() {
        super(ErrorCode.BAD_REQUEST);
    }

    public InvalidParamException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidParamException(String errorMsg) {
        super(errorMsg, ErrorCode.BAD_REQUEST);
    }

    public InvalidParamException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
