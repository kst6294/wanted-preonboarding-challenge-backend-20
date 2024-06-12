package io.github.potatoy.wanted_preonboarding_challenge.error.handler;

import io.github.potatoy.wanted_preonboarding_challenge.error.dto.ErrorResponse;
import io.github.potatoy.wanted_preonboarding_challenge.error.exception.BadRequestException;
import io.github.potatoy.wanted_preonboarding_challenge.error.exception.ConflictException;
import io.github.potatoy.wanted_preonboarding_challenge.error.exception.ForbiddenException;
import io.github.potatoy.wanted_preonboarding_challenge.error.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST) // status: 400
  public ErrorResponse handleException(BadRequestException exception) {

    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN) // status: 403
  public ErrorResponse handleException(ForbiddenException exception) {
    return new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND) // status: 404
  public ErrorResponse handleException(NotFoundException exception) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
  }

  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT) // status: 409
  public ErrorResponse handleException(ConflictException exception) {
    return new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());
  }

  //  @ExceptionHandler(Exception.class)
  //  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // status: 500, 이외의 예상치 못한 모든 예외
  //  public ErrorResponse handleException(Exception exception) {
  //    log.error(exception.toString());
  //    return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR");
  //  }
}
