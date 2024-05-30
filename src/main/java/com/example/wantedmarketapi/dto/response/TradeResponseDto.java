package com.example.wantedmarketapi.dto.response;

import lombok.*;

public class TradeResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateTradeResponse {
        Long sellerId;
        Long purchaserId;
        String productName;
    }

}
