package com.sunyesle.wanted_market.dto;

import lombok.Getter;

@Getter
public class ProductRequest {
    private final String name;
    private final Integer price;

    public ProductRequest(String name, Integer price) {

        this.name = name;
        this.price = price;
    }
}
