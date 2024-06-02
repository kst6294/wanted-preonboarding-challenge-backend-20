package io.github.potatoy.wanted_preonboarding_challenge.market;

import io.github.potatoy.wanted_preonboarding_challenge.error.exception.BadRequestException;
import io.github.potatoy.wanted_preonboarding_challenge.market.dto.ProductDto;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.Product;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.ProductRepository;
import io.github.potatoy.wanted_preonboarding_challenge.market.exception.ProductUpdateFailException;
import io.github.potatoy.wanted_preonboarding_challenge.market.util.ProductUtil;
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
  private final ProductUtil productUtil;

  public Product saveProduct(ProductDto.RegisterRequest dto) {
    User user = securityUtil.getCurrentUser();

    Product product =
        Product.builder().name(dto.getName()).price(dto.getPrice()).sellerUser(user).build();

    productRepository.save(product);

    return product;
  }

  public Product reserveProduct(Long productId) {
    User user = securityUtil.getCurrentUser();
    Product product = productUtil.findById(productId);

    if (product.getSellerUser().equals(user)) { // 본인 상품에 예약 요청시 예외 발생
      log.warn(
          "reserveProduct: Attempting to make a reservation for your own product. userId={}, productId={}",
          user.getId(),
          product.getId());
      throw new BadRequestException("Attempting to make a reservation for your own product.");
    }
    if (product.getBuyerUser() != null) { // 이미 예약자가 있다면 예외 발생
      log.warn(
          "reserveProduct: There is already a reservation. userId={}, productId={}",
          user.getId(),
          product.getId());
      throw new ProductUpdateFailException("There is already a reservation.");
    }

    product.updateBuyerUser(user);
    productRepository.save(product);

    return product;
  }
}
