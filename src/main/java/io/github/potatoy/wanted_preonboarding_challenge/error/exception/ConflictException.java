package io.github.potatoy.wanted_preonboarding_challenge.error.exception;

public class ConflictException extends RuntimeException {

  public ConflictException() {
    super("CONFLICT");
  }

  public ConflictException(String code) {
    super(code);
  }
}
