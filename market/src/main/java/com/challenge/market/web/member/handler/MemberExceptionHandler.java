package com.challenge.market.web.member.handler;

import com.challenge.market.domain.member.constants.MemberErrorResult;
import com.challenge.market.domain.member.exception.MemberNotFoundException;
import com.challenge.market.web.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("Method Argument Not Valid Exception:", ex);
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", ex.getMessage());
        return new ResponseEntity<>(errorResponse, headers, status);    }


    @ExceptionHandler({MemberNotFoundException.class})
    public ResponseEntity<ErrorResponse> memberNotFoundExceptionHandler(MemberNotFoundException exception){
        log.warn("@@@@@@@@@Member not found Exception:" ,exception);
        return createErrorResponseEntity(exception.getError());
    }

    private ResponseEntity<ErrorResponse> createErrorResponseEntity(MemberErrorResult errorResult){
        return ResponseEntity.status(errorResult.getStatus())
                .body(new ErrorResponse(errorResult.name(),errorResult.getMessage()));

    }

}
