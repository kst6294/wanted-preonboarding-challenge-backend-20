package com.example.wantedmarketapi.converter;

import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.Trade;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.response.TradeResponseDto.*;
import org.springframework.stereotype.Component;

@Component
public class TradeConverter {

    public static Trade toTrade(Member member, Product product) {
        return Trade.builder()
                .product(product)
                .seller(product.getSeller())
                .purchaser(member)
                .build();
    }

    public static CreateTradeResponse toCreateTradeResponse(Trade trade) {
        return CreateTradeResponse.builder()
                .productName(trade.getProduct().getName())
                .sellerId(trade.getSeller().getId())
                .purchaserId(trade.getPurchaser().getId())
                .build();
    }

}
