package org.example.wantedmarket.dto.common;

import lombok.Getter;
import org.example.wantedmarket.exception.ErrorCode;

@Getter
public class ErrorResponseDto extends ResponseDto {

    private final ErrorCode errorCode;
    private final String errorMessage;

    public ErrorResponseDto(ErrorCode errorCode, String errorMessage) {
        super(false);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
