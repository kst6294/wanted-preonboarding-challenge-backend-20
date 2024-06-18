package com.example.wantedmarketapi.domain.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCommand {
    private final String name;
    private final Integer price;
    private final Long userId;

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .userId(userId)
                .build();
    }

}
