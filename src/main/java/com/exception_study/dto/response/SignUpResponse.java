package com.exception_study.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class SignUpResponse {
    private String message;


    public static SignUpResponse of(String message){
        return new SignUpResponse(message);
    }
}

