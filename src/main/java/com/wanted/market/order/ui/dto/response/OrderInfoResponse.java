package com.wanted.market.order.ui.dto.response;

public record OrderInfoResponse(
        Long id,
        Long sellerId,
        String sellerName,
        Long buyerId,
        String buyerName,
        String productName,
        String productStatus
) {
}
