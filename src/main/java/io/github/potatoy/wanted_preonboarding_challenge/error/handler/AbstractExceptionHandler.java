package io.github.potatoy.wanted_preonboarding_challenge.error.handler;

import io.github.potatoy.wanted_preonboarding_challenge.error.dto.ErrorResponse;
import io.github.potatoy.wanted_preonboarding_challenge.util.EnvProperties;

public abstract class AbstractExceptionHandler<T extends Exception> {

  public static final String PROD = "prod";

  protected final EnvProperties envProperties;

  public AbstractExceptionHandler(EnvProperties envProperties) {
    this.envProperties = envProperties;
  }

  public abstract ErrorResponse handleException(T exception);
}
