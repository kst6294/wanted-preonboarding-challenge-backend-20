package io.github.potatoy.wanted_preonboarding_challenge.market.exception;

import io.github.potatoy.wanted_preonboarding_challenge.error.exception.NotFoundException;

public class NotFoundProductException extends NotFoundException {

  public NotFoundProductException(String message) {
    super(message);
  }
}
