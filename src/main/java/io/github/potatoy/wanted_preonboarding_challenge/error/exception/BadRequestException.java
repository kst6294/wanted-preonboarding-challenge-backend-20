package io.github.potatoy.wanted_preonboarding_challenge.error.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException() {
    super("BAD_REQUEST");
  }

  public BadRequestException(String code) {
    super(code);
  }
}
