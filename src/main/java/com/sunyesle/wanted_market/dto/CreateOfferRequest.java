package com.sunyesle.wanted_market.dto;

import lombok.Getter;

@Getter
public class CreateOfferRequest {
    private final Long productId;
    private final Integer quantity;

    public CreateOfferRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
