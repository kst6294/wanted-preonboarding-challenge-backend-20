package kr.co.wanted.market.trade.dto;

import kr.co.wanted.market.trade.entity.Trade;
import kr.co.wanted.market.trade.enums.TradeState;

public record TradeHistory(Long tradeId,
                           TradeState state,
                           Long productId,
                           String productName,
                           Long sellerId,
                           Long buyerId,
                           Long price) {

    public static TradeHistory fromEntity(Trade trade) {

        return new TradeHistory(
                trade.getId(),
                trade.getState(),
                trade.getProduct().getId(),
                trade.getProduct().getName(),
                trade.getSeller().getId(),
                trade.getBuyer().getId(),
                trade.getPrice()
        );
    }
}
