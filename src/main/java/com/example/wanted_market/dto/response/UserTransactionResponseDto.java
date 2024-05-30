package com.example.wanted_market.dto.response;

import com.example.wanted_market.type.EOrderStatus;
import com.example.wanted_market.type.EProductStatus;
import lombok.Builder;

@Builder
public record UserTransactionResponseDto(
        String userRole, // "Buyer" or "Seller"
        Long productId,
        String productName,
        int price,
        EProductStatus productStatus,
        EOrderStatus orderStatus,
        String orderTime
) {
}
