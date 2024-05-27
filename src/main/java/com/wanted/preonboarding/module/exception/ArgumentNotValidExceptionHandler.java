package com.wanted.preonboarding.module.exception;

import com.wanted.preonboarding.module.common.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ArgumentNotValidExceptionHandler {

    private static final String SPACE = " ";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return handleValidationException(ex.getBindingResult());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        return handleValidationException(ex.getBindingResult());
    }

    private ResponseEntity<ErrorResponse> handleValidationException(BindingResult bindingResult) {
        String exceptionClassName = bindingResult.getClass().getSimpleName();

        String errorMsg = bindingResult.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        return ((FieldError) error).getField() + SPACE + error.getDefaultMessage();
                    } else {
                        return error.getObjectName() + SPACE + error.getDefaultMessage();
                    }
                })
                .collect(Collectors.joining(SPACE));

        ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, exceptionClassName, errorMsg);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


}
