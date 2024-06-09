package io.github.potatoy.wanted_preonboarding_challenge.token.exception;

import io.github.potatoy.wanted_preonboarding_challenge.error.exception.BadRequestException;

public class InvalidTokenException extends BadRequestException {

  public InvalidTokenException() {
    super("INVALID_TOKEN");
  }
}
