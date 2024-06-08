package io.github.potatoy.wanted_preonboarding_challenge.error.handler;

import io.github.potatoy.wanted_preonboarding_challenge.error.dto.ErrorResponse;
import io.github.potatoy.wanted_preonboarding_challenge.error.exception.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ConflictExceptionHandler extends AbstractExceptionHandler<ConflictException> {

  @Override
  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handleException(ConflictException exception) {
    return new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());
  }
}
