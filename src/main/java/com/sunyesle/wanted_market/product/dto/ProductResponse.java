package com.sunyesle.wanted_market.product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class ProductResponse {
    private final Long id;

    @JsonCreator
    public ProductResponse(Long id) {
        this.id = id;
    }
}
