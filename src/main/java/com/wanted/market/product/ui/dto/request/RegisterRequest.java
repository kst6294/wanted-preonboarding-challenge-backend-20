package com.wanted.market.product.ui.dto.request;

public record RegisterRequest(
        String name,
        Integer price,
        Integer quantity
) {
}
