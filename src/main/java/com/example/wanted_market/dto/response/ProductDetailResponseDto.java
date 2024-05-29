package com.example.wanted_market.dto.response;

import com.example.wanted_market.type.EProductStatus;
import lombok.Builder;

@Builder
public record ProductDetailResponseDto(
        String name,
        int price,
        EProductStatus status,
        String sellerNickname
) {
}
