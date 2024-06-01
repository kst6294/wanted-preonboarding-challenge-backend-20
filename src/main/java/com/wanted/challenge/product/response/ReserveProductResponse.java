package com.wanted.challenge.product.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wanted.challenge.product.model.Price;

public record ReserveProductResponse(Long productId, String name, Integer price) {

    @QueryProjection
    public ReserveProductResponse(Long productId, String name, Price price) {
        this(productId, name, price.value());
    }
}
