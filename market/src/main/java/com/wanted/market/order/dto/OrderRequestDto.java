package com.wanted.market.order.dto;

import lombok.Getter;


@Getter
public class OrderRequestDto {

    private final Integer productId;
    private final int quantity;

    public OrderRequestDto(Integer productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
