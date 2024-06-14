package io.github.potatoy.wanted_preonboarding_challenge.market;

import io.github.potatoy.wanted_preonboarding_challenge.market.dto.ProductDto;
import io.github.potatoy.wanted_preonboarding_challenge.market.dto.ProductDto.ProductIdParams;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.Product;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.ProductRepository;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.State;
import io.github.potatoy.wanted_preonboarding_challenge.market.exception.ProductForbiddenException;
import io.github.potatoy.wanted_preonboarding_challenge.market.exception.ProductNotBuyerException;
import io.github.potatoy.wanted_preonboarding_challenge.market.exception.ProductUpdateFailException;
import io.github.potatoy.wanted_preonboarding_challenge.market.util.ProductUtil;
import io.github.potatoy.wanted_preonboarding_challenge.security.SecurityUtil;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MarketService {

  private final ProductRepository productRepository;
  private final SecurityUtil securityUtil;
  private final ProductUtil productUtil;

  @Transactional(readOnly = true)
  public List<Product> getAllProducts() {
    log.info("getProduct: View all products.");

    return productRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Product getProduct(Long productId) {
    log.info("getProduct: Product inquiry. productId={}", productId);

    return productUtil.findById(productId);
  }

  public Product saveProduct(ProductDto.RegisterRequest dto) {
    User user = securityUtil.getCurrentUser();
    log.info("saveProduct: Save the product. userId={}", user.getId());

    Product product =
        Product.builder().name(dto.getName()).price(dto.getPrice()).sellerUser(user).build();

    productRepository.save(product);

    return product;
  }

  public Product reserveProduct(Long productId) {
    User user = securityUtil.getCurrentUser();
    log.info(
        "reserveProduct: Reserve the product. userId={}, productId={}", user.getId(), productId);
    Product product = productUtil.findById(productId);

    if (product.getSellerUser().equals(user)) { // 본인 상품에 예약 요청시 예외 발생
      log.warn(
          "reserveProduct: Attempting to make a reservation for your own product. userId={}, productId={}",
          user.getId(),
          product.getId());
      throw new ProductUpdateFailException();
    }
    if (product.getBuyerUser() != null) { // 이미 예약자가 있다면 예외 발생
      log.warn(
          "reserveProduct: There is already a reservation. userId={}, productId={}",
          user.getId(),
          product.getId());
      throw new ProductUpdateFailException();
    }

    product.updateBuyerUser(user);
    productRepository.save(product);

    return product;
  }

  public List<Product> getSellerBuyerRecord(ProductIdParams dto) {
    User user = securityUtil.getCurrentUser();
    log.info(
        "getSellerBuyerRecord: Check transaction history between traders. userId={}, productId={}",
        user.getId(),
        dto.getProductId());
    Product product = productUtil.findById(dto.getProductId());

    if (product.getBuyerUser() == null) { // 예약자 정보가 없는 경우 예외 발생
      log.warn(
          "getSellerBuyerRecord: Reservation information does not exist. userId={}, productId={}",
          user.getId(),
          dto.getProductId());
      throw new ProductNotBuyerException();
    }

    // 양쪽 모두 해당되지 않는다면 예외 처리
    if (!product.isSellerEquals(user) && !product.isBuyerEquals(user)) {
      log.warn(
          "getSellerBuyerRecord: Product information is not accessible. userId={}, productId={}",
          user.getId(),
          dto.getProductId());
      throw new ProductForbiddenException();
    }

    Set<Product> result = new LinkedHashSet<>();

    result.addAll(
        productRepository.findBySellerUserAndBuyerUser(
            product.getSellerUser(), product.getBuyerUser()));
    result.addAll(
        productRepository.findByBuyerUserAndSellerUser(
            product.getBuyerUser(), product.getSellerUser()));

    return result.stream().toList();
  }

  public List<Product> getMyRecord() {
    User user = securityUtil.getCurrentUser();
    log.info("getMyRecord: Check my records. userId={}", user.getId());

    List<Product> products = new ArrayList<>();

    products.addAll(user.getSaleList());
    products.addAll(user.getPurchaseList());

    return products;
  }

  public Product approveSale(Long productId) {
    User user = securityUtil.getCurrentUser();
    log.info("approveSale: Approved for sale. userId={}, productId={}", user.getId(), productId);
    Product product = productUtil.findById(productId);

    if (!product.isSellerEquals(user)) {
      log.warn(
          "approveSale: You do not have permission to access the product. userId={}, productId={}",
          user.getId(),
          product.getId());
      throw new ProductForbiddenException();
    }

    if (product.getState() != State.RESERVATION) {
      log.warn(
          "approveSale: Product information cannot be changed. userId={}, productId={}",
          user.getId(),
          product.getId());
      throw new ProductUpdateFailException();
    }
    product.setSoldOut();

    return product;
  }
}
