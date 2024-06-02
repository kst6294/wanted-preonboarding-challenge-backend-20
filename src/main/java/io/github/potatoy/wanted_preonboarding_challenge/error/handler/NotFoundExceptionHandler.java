package io.github.potatoy.wanted_preonboarding_challenge.error.handler;

import io.github.potatoy.wanted_preonboarding_challenge.error.dto.ErrorResponse;
import io.github.potatoy.wanted_preonboarding_challenge.error.exception.NotFoundException;
import io.github.potatoy.wanted_preonboarding_challenge.util.EnvProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotFoundExceptionHandler extends AbstractExceptionHandler<NotFoundException> {

  public NotFoundExceptionHandler(EnvProperties envProperties) {
    super(envProperties);
  }

  @Override
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleException(NotFoundException exception) {
    if (envProperties.getMode().equals(PROD)) {
      return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.toString());
  }
}
