package com.wanted.challenge.exception;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException customException) {

        ExceptionStatus exceptionStatus = customException.getExceptionStatus();

        return ResponseEntity.status(exceptionStatus.getHttpStatus())
                .body(exceptionStatus.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleBindException(BindException exception) {
        MultiValueMap<String, String> errorMessages = new LinkedMultiValueMap<>();

        exception.getFieldErrors()
                .forEach(fieldError -> errorMessages.add(fieldError.getField(), getDefaultMessage(fieldError)));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessages.toString());
    }

    private static String getDefaultMessage(FieldError fieldError) {
        if (fieldError.isBindingFailure()) {
            return "유효한 값이 아닙니다";
        }

        String defaultMessage = fieldError.getDefaultMessage();

        if (Objects.isNull(defaultMessage)) {
            return "조건이 충족되지 않았습니다";
        }

        return defaultMessage;
    }
}
