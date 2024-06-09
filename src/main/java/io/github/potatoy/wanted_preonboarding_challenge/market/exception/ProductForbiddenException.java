package io.github.potatoy.wanted_preonboarding_challenge.market.exception;

import io.github.potatoy.wanted_preonboarding_challenge.error.exception.ForbiddenException;

public class ProductForbiddenException extends ForbiddenException {

  public ProductForbiddenException() {
    super("PRODUCT_FORBIDDEN");
  }
}
