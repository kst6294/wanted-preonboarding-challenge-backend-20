package com.chaewon.wanted.common;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ResponseDto {
    private HttpStatus status;
    private String message;

    public static ResponseEntity<ResponseDto> of(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(ResponseDto.builder()
                        .message(message)
                        .status(status)
                        .build()
                );
    }
}