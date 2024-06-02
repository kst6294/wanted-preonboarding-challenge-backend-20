package io.github.potatoy.wanted_preonboarding_challenge.error.handler;

import io.github.potatoy.wanted_preonboarding_challenge.error.dto.ErrorResponse;
import io.github.potatoy.wanted_preonboarding_challenge.error.exception.ConflictException;
import io.github.potatoy.wanted_preonboarding_challenge.util.EnvProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ConflictExceptionHandler extends AbstractExceptionHandler<ConflictException> {

  public ConflictExceptionHandler(EnvProperties envProperties) {
    super(envProperties);
  }

  @Override
  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handleException(ConflictException exception) {
    if (envProperties.getMode().equals(PROD)) {
      return new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());
    }
    return new ErrorResponse(HttpStatus.CONFLICT.value(), exception.toString());
  }
}
