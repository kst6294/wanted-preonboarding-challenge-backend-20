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
    private final Integer quantity;
    private final Integer availableQuantity;
    private final ProductStatus status;

    @Builder
    private ProductDetailResponse(Long id, String name, Integer price, Integer quantity, Integer availableQuantity, ProductStatus status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
        this.status = status;
    }

    public static ProductDetailResponse of(Product product) {
        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .availableQuantity(product.getAvailableQuantity())
                .status(product.getStatus()).build();
    }
}
