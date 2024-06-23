package kr.co.wanted.market.trade.dto;

import jakarta.validation.constraints.NotNull;
import kr.co.wanted.market.trade.entity.Trade;
import kr.co.wanted.market.trade.enums.TradeState;
import lombok.Builder;

@Builder
public record Purchase(Long id,
                       Long sellerId,

                       @NotNull(message = "필수입니다.")
                       Long productId,

                       TradeState state,
                       Long price,
                       Long quantity) {

    public static Purchase fromEntity(Trade trade) {

        return new Purchase(
                trade.getId(),
                trade.getSeller().getId(),
                trade.getProduct().getId(),
                trade.getState(),
                trade.getPrice(),
                trade.getQuantity()
        );
    }
}
