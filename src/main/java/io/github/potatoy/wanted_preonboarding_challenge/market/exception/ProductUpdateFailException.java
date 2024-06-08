package io.github.potatoy.wanted_preonboarding_challenge.market.exception;

import io.github.potatoy.wanted_preonboarding_challenge.error.exception.ConflictException;

public class ProductUpdateFailException extends ConflictException {

  public ProductUpdateFailException() {
    super("PRODUCT_UPDATE_FAIL");
  }
}
