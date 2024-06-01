package com.example.wanted.product.service.response;

import com.example.wanted.product.domain.Product;
import com.example.wanted.product.domain.ProductSellingStatus;
import com.example.wanted.user.controller.response.UserResponse;
import com.example.wanted.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {
    private Long id;
    private UserResponse seller;
    private String name;
    private int price;
    private int quantity;
    private ProductSellingStatus productSellingStatus;

    @Builder
    public ProductResponse(
            Long id,
            String name,
            int quantity,
            int price,
            UserResponse seller,
            ProductSellingStatus productSellingStatus) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.seller = seller;
        this.productSellingStatus = productSellingStatus;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .seller(UserResponse.from(product.getSeller()))
                .productSellingStatus(product.getSellingStatus())
                .build();
    }
}
