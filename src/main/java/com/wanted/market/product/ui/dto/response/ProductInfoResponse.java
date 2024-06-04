package com.wanted.market.product.ui.dto.response;

public record ProductInfoResponse(
        Long id,
        String name,
        Integer price,
        String status,
        Integer version
) {
}
