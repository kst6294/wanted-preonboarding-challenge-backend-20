package com.wanted.preonboarding.module.common.payload;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Response {

    private int status;
    private String message;


    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

