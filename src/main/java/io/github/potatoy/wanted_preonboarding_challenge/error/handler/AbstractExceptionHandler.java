package io.github.potatoy.wanted_preonboarding_challenge.error.handler;

import io.github.potatoy.wanted_preonboarding_challenge.error.dto.ErrorResponse;

public abstract class AbstractExceptionHandler<T extends Exception> {

  public abstract ErrorResponse handleException(T exception);
}
