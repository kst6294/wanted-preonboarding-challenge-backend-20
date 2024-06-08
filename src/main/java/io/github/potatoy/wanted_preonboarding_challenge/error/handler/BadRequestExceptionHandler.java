package io.github.potatoy.wanted_preonboarding_challenge.error.handler;

import io.github.potatoy.wanted_preonboarding_challenge.error.dto.ErrorResponse;
import io.github.potatoy.wanted_preonboarding_challenge.error.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestExceptionHandler extends AbstractExceptionHandler<BadRequestException> {

  @Override
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleException(BadRequestException exception) {

    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }
}
