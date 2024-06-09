package io.github.potatoy.wanted_preonboarding_challenge.user.exception;

import io.github.potatoy.wanted_preonboarding_challenge.error.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException() {
    super("USER_NOT_FOUND");
  }
}
