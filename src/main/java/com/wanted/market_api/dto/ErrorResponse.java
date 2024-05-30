package com.wanted.market_api.dto;

import com.wanted.market_api.constant.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private int statusCode;
    private String serverCode;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponse.builder()
                        .statusCode(e.getHttpStatus().value())
                        .serverCode(e.getCode())
                        .message(e.getMessage())
                        .build()
                );
    }
}
