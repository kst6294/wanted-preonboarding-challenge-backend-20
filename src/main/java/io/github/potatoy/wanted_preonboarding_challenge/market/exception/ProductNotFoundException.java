package io.github.potatoy.wanted_preonboarding_challenge.market.exception;

import io.github.potatoy.wanted_preonboarding_challenge.error.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {

  public ProductNotFoundException() {
    super("PRODUCT_NOT_FOUND");
  }
}
