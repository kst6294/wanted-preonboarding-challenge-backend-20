package io.github.potatoy.wanted_preonboarding_challenge.error.handler;

import io.github.potatoy.wanted_preonboarding_challenge.error.dto.ErrorResponse;
import io.github.potatoy.wanted_preonboarding_challenge.error.exception.ForbiddenException;
import io.github.potatoy.wanted_preonboarding_challenge.util.EnvProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ForbiddenExceptionHandler extends AbstractExceptionHandler<ForbiddenException> {

  public ForbiddenExceptionHandler(EnvProperties envProperties) {
    super(envProperties);
  }

  @Override
  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorResponse handleException(ForbiddenException exception) {
    if (envProperties.getMode().equals(PROD)) {
      return new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }
    return new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.toString());
  }
}
