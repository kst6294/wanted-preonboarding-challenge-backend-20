package io.github.potatoy.wanted_preonboarding_challenge.market.dto;

import io.github.potatoy.wanted_preonboarding_challenge.market.entity.Product;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.State;
import io.github.potatoy.wanted_preonboarding_challenge.user.dto.UserResponse;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

public class ProductDto {

  @Getter
  @Setter
  public static class RegisterRequest {

    @NotBlank private String name;
    private Long price;
  }

  @Getter
  @Setter
  public static class TransactionsRequest {

    private Long productId;
  }

  @Getter
  @Setter
  public static class ProductResponse {

    private Long id;
    private String name;
    private Long price;
    private LocalDateTime createdAt;
    private State state;
    private UserResponse sellerUser;
    private UserResponse buyerUser;

    public ProductResponse(Product product) {
      this.id = product.getId();
      this.name = product.getName();
      this.price = product.getPrice();
      this.createdAt = product.getCreatedAt();
      this.state = product.getState();
      this.sellerUser = new UserResponse(product.getSellerUser());
      if (product.getBuyerUser() != null) {
        this.buyerUser = new UserResponse(product.getBuyerUser());
      }
    }
  }
}
