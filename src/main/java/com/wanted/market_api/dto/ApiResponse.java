package com.wanted.market_api.dto;

import com.wanted.market_api.constant.ResponseMessage;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;

    public ApiResponse() {
        this.statusCode = ResponseMessage.SUCCESS.getCode();
        this.message = ResponseMessage.SUCCESS.getMessage();
    }

    public ApiResponse(ResponseMessage responseMessage) {
    }

    public ApiResponse(T data) {
        this.statusCode = ResponseMessage.SUCCESS.getCode();
        this.message = ResponseMessage.SUCCESS.getMessage();
        this.data = data;
    }


}

