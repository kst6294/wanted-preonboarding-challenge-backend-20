package com.wanted.market.global.common.response;

import com.wanted.market.global.common.code.ResponseCode;
import lombok.Getter;

@Getter
public class BaseResponse {

    private int status;
    private String label;
    private String message;

    public BaseResponse(ResponseCode response) {
        this.status = response.getStatus();
        this.label = response.getLabel();
        this.message = response.getMessage();
    }
}
