package io.github.potatoy.wanted_preonboarding_challenge.error.exception;

public class ForbiddenException extends RuntimeException {

  public ForbiddenException() {
    super("FORBIDDEN");
  }

  public ForbiddenException(String code) {
    super(code);
  }
}
