package com.example.wanted_market.dto;

import com.example.wanted_market.exception.ErrorCode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ExceptionDto {
    @Min(40000)
    private Integer code;
    @NotBlank
    private String message;

    public ExceptionDto(ErrorCode errorCode, String message){
        this.code = errorCode.getCode();
        this.message = message;
    }
}
