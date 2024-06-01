package com.wanted.market.domain.product.request;

import lombok.Getter;

@Getter
public class RegisterRequest {

    private String name;

    private long price;

    private String description;

    private int quantity;
}
