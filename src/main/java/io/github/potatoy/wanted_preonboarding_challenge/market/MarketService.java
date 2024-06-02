package io.github.potatoy.wanted_preonboarding_challenge.market;

import io.github.potatoy.wanted_preonboarding_challenge.market.dto.ProductDto;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.Product;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.ProductRepository;
import io.github.potatoy.wanted_preonboarding_challenge.security.SecurityUtil;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MarketService {

  private final ProductRepository productRepository;
  private final SecurityUtil securityUtil;

  public Product saveProduct(ProductDto.RegisterRequest dto) {
    User user = securityUtil.getCurrentUser();

    Product product =
        Product.builder().name(dto.getName()).price(dto.getPrice()).sellerUser(user).build();

    productRepository.save(product);

    return product;
  }
}
