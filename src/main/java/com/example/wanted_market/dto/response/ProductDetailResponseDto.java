package com.example.wanted_market.dto.response;

import com.example.wanted_market.type.EOrderStatus;
import com.example.wanted_market.type.EProductStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ProductDetailResponseDto(
        ProductDetailDto productDetail,
        List<TransactionDto> transactions

) {
    @Builder
    public record ProductDetailDto(
            String name,
            int price,
            EProductStatus status,
            String sellerNickname
    ){ }

    @Builder
    public record TransactionDto(
            Long orderId,
            String buyerNickname,
            EOrderStatus orderStatus,
            String orderTime
    ){ }
}


