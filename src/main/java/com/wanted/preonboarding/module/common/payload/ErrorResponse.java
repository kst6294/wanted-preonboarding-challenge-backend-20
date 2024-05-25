package com.wanted.preonboarding.module.common.payload;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@SuperBuilder
public class ErrorResponse extends Response {

    private final static String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
    private final String error;

    public ErrorResponse(int status, String message, String error) {
        super(status, message);
        this.error = error;
    }


    public static ErrorResponse of(HttpStatus httpStatus, String exceptionClassName, String detailMessage) {
        return ErrorResponse.builder()
                .error(exceptionClassName)
                .status(httpStatus.value())
                .message(detailMessage)
                .build();
    }

}
