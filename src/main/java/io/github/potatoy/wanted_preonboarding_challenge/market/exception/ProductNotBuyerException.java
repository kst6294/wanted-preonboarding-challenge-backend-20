package io.github.potatoy.wanted_preonboarding_challenge.market.exception;

import io.github.potatoy.wanted_preonboarding_challenge.error.exception.BadRequestException;

public class ProductNotBuyerException extends BadRequestException {

  public ProductNotBuyerException() {
    super("PRODUCT_NOT_BUYER_USER");
  }
}
