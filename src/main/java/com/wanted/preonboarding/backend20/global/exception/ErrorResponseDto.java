package com.wanted.preonboarding.backend20.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ErrorResponseDto {
    private int status;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseDto> toResponseEntity(ErrorCode e, String message) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseDto.builder()
                        .status(e.getStatus().value())
                        .code(e.name())
                        .message(message)
                        .build()
                );
    }
}
