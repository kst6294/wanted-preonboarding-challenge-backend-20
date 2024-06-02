package io.github.potatoy.wanted_preonboarding_challenge.market.util;

import io.github.potatoy.wanted_preonboarding_challenge.market.entity.Product;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.ProductRepository;
import io.github.potatoy.wanted_preonboarding_challenge.market.exception.NotFoundProductException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProductUtil {

  private final ProductRepository productRepository;

  public Product findById(Long productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(
            () -> {
              log.warn("ProductUtil: Product id not found. productId={}", productId);
              return new NotFoundProductException("Product id not found.");
            });
  }
}
