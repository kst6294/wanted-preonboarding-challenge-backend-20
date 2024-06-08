package io.github.potatoy.wanted_preonboarding_challenge.error.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException() {
    super("NOT_FOUND");
  }

  public NotFoundException(String code) {
    super(code);
  }
}
