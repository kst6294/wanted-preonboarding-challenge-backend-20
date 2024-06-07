package com.sunyesle.wanted_market.dto;

import lombok.Getter;

@Getter
public class ProductRequest {
    private final String name;
    private final Integer price;
    private final Integer quantity;

    public ProductRequest(String name, Integer price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
