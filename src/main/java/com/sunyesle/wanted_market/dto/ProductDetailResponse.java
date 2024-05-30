package com.sunyesle.wanted_market.dto;

import com.sunyesle.wanted_market.entity.Product;
import com.sunyesle.wanted_market.enums.ProductStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDetailResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final ProductStatus status;

    @Builder
    private ProductDetailResponse(Long id, String name, Integer price, ProductStatus status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public static ProductDetailResponse of(Product product) {
        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus()).build();
    }
}
