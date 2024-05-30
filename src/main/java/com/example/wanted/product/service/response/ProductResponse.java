package com.example.wanted.product.service.response;

import com.example.wanted.product.domain.Product;
import com.example.wanted.product.domain.ProductSellingStatus;
import com.example.wanted.user.controller.response.UserResponse;
import com.example.wanted.user.domain.User;
import lombok.Builder;

public class ProductResponse {
    private Long id;
    private UserResponse seller;
    private String name;
    private int quantity;
    private ProductSellingStatus productSellingStatus;

    @Builder
    public ProductResponse(
            Long id,
            String name,
            int quantity,
            UserResponse seller,
            ProductSellingStatus productSellingStatus) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.seller = seller;
        this.productSellingStatus = productSellingStatus;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .seller(UserResponse.from(product.getSeller()))
                .productSellingStatus(product.getSellingStatus())
                .build();
    }
}
