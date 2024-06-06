package com.wanted.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private int status;
    private String stackTrace;

}
