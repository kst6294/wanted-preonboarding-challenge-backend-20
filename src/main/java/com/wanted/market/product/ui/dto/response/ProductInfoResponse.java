package com.wanted.market.product.ui.dto.response;

public record ProductInfoResponse(
        String name,
        Integer price,
        String status
) {
}
