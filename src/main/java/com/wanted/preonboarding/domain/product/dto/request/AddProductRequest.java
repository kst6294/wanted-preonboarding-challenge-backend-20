package com.wanted.preonboarding.domain.product.dto.request;

import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.product.entity.ProductState;
import com.wanted.preonboarding.domain.user.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddProductRequest {

    @NotBlank(message = "제품명은 필수 입력사항입니다.")
    private String name; // 제품명

    @NotNull(message = "가격은 필수 입력사항입니다.")
    @Min(value = 0, message = "가격은 0 이상이여야 합니다.")
    @Max(value = 2_100_000_000, message = "가격은 최대 21억까지 가능합니다.")
    private Integer price; // 가격

    @NotNull(message = "재고는 필수 입력사항입니다.")
    @Min(value = 0, message = "재고는 0 이상이여야 합니다.")
    @Max(value = 2_100_000_000, message = "재고는 최대 21억까지 가능합니다.")
    private Integer quantity; // 재고

    public Product toEntity(User user) {
        return Product.builder()
                .user(user)
                .name(name)
                .price(price)
                .quantity(quantity)
                .state(ProductState.SALE)
                .build();
    }
}
