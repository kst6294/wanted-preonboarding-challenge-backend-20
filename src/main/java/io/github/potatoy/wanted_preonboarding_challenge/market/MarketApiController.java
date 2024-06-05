package io.github.potatoy.wanted_preonboarding_challenge.market;

import io.github.potatoy.wanted_preonboarding_challenge.market.dto.ProductDto;
import io.github.potatoy.wanted_preonboarding_challenge.market.dto.ProductDto.ProductIdParams;
import io.github.potatoy.wanted_preonboarding_challenge.market.dto.ProductDto.ProductResponse;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.Product;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/market")
public class MarketApiController {

  private final MarketService marketService;

  @PostMapping("/products") // 새로운 상품 등록
  public ResponseEntity<ProductDto.ProductResponse> registrationProduct(
      @Validated @RequestBody ProductDto.RegisterRequest request) {

    Product product = marketService.saveProduct(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(product));
  }

  @PostMapping("/products/{productId}/reserve") // 상품 예약 등록
  public ResponseEntity<ProductDto.ProductResponse> reserveProduct(@PathVariable Long productId) {
    Product product = marketService.reserveProduct(productId);

    return ResponseEntity.status(HttpStatus.OK).body(new ProductResponse(product));
  }

  @GetMapping("/products/transactions") // 거래 내역
  public ResponseEntity<List<ProductDto.ProductResponse>> getProducts(ProductIdParams request) {
    List<ProductDto.ProductResponse> response = new ArrayList<>();

    if (request.getProductId() == null) {
      List<Product> products = marketService.getMyRecord();

      for (Product product : products) {
        response.add(new ProductResponse(product));
      }
    } else {
      List<Product> products = marketService.getSellerBuyerRecord(request);
      for (Product product : products) {
        response.add(new ProductResponse(product));
      }
    }

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/products/{productId}/purchase")
  public ResponseEntity<ProductResponse> approveSale(@PathVariable Long productId) { // 판매 승인
    Product product = marketService.approveSale(productId);

    return ResponseEntity.status(HttpStatus.OK).body(new ProductResponse(product));
  }
}
