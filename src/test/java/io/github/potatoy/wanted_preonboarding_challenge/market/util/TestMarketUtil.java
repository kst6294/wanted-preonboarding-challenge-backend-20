package io.github.potatoy.wanted_preonboarding_challenge.market.util;

import io.github.potatoy.wanted_preonboarding_challenge.market.entity.Product;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.ProductRepository;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestMarketUtil {

  private ProductRepository productRepository;

  /**
   * 새로운 상품 생성
   *
   * @param sellerUser 팬매자 User 객체
   * @param name 상품 이름
   * @param price 상품 가격
   * @param buyerUser 구매자 User 객체, null시 미포함
   * @return Product
   */
  public Product createProduct(User sellerUser, String name, Long price, User buyerUser) {
    Product product = Product.builder().sellerUser(sellerUser).name(name).price(price).build();

    if (buyerUser != null) {
      product.updateBuyerUser(buyerUser);
    }

    productRepository.save(product);
    return product;
  }
}
