package com.wanted.market.global.web.exceptionhandler;

import com.wanted.market.common.exception.UnauthorizedRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    public ResponseEntity<?> handleUncaughtException(final RuntimeException e) {
        logger.error("Uncaught exception", e);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<?> handleUnauthorizedRequestException(final UnauthorizedRequestException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }
}
