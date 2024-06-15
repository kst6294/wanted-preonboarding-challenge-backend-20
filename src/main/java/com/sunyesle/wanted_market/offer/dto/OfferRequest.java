package com.sunyesle.wanted_market.offer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class OfferRequest {
    private final Long productId;

    @JsonCreator
    public OfferRequest(Long productId) {
        this.productId = productId;
    }
}
