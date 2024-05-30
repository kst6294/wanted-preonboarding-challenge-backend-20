package com.example.wantedmarketapi.converter;

import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.Trade;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.response.TradeResponseDto.*;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public static PurchaseProductListResponse toPurchaseProductListResponse(Trade trade) {
        return PurchaseProductListResponse.builder()
                .productId(trade.getProduct().getId())
                .productName(trade.getProduct().getName())
                .productPrice(trade.getProduct().getPrice())
                .build();
    }

    public static List<PurchaseProductListResponse> toGetPurchaseProductListResponseList(List<Trade> tradeList) {
        return tradeList.stream()
                .map(trade -> TradeConverter.toPurchaseProductListResponse(trade))
                .toList();
    }

}
