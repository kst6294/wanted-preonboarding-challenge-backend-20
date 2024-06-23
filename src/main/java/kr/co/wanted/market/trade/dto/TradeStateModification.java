package kr.co.wanted.market.trade.dto;

import kr.co.wanted.market.trade.enums.TradeState;

public record TradeStateModification(Long id, TradeState state) {
}
