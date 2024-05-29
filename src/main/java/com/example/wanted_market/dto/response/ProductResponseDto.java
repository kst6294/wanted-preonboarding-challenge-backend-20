package com.example.wanted_market.dto.response;

import com.example.wanted_market.type.EProductStatus;

public record ProductResponseDto(
        Long productId,
        String name,
        int price,
        EProductStatus status
) {
}
