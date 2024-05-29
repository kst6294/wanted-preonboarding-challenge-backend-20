package com.example.wanted.product.domain;

import com.example.wanted.user.infrastucture.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreate {
    private String name;
    private int price;
    private int quantity;

    @Builder
    public ProductCreate(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
