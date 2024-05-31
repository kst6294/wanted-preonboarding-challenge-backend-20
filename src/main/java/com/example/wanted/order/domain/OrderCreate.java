package com.example.wanted.order.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreate {
    long productId;

    @Builder
    public OrderCreate(long productId) {
        this.productId = productId;
    }
}
