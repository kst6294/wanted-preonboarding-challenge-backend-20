package com.example.wantedmarketapi.interfaces.product;

import com.example.wantedmarketapi.domain.product.ProductCommand;
import com.example.wantedmarketapi.domain.product.ProductInfo;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

public class ProductDto {

    @Getter
    public static class RegisterRequest {

        @NotEmpty(message = "name은 필수입력값입니다.")
        private String name;

        @NotEmpty(message = "price는 필수입력값입니다.")
        private Integer price;

        @NotEmpty(message = "userId는 필수입력값입니다.")
        private Long userId;

        public ProductCommand toCommand() {
            return ProductCommand.builder()
                    .name(name)
                    .price(price)
                    .userId(userId)
                    .build();
        }
    }

    @Getter
    public static class RegisterResponse {
        private String name;
        private Integer price;
        private Long userId;

        public RegisterResponse(ProductInfo productInfo) {
            this.name = productInfo.getName();
            this.price = productInfo.getPrice();
            this.userId = productInfo.getUserId();
        }
    }

}
