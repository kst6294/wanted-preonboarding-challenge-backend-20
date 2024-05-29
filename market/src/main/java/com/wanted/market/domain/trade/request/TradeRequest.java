package com.wanted.market.domain.trade.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TradeRequest {

    private long productNo;

    private long tradeNo;
}
