package com.wanted.market.domain.trade.dto;

import com.wanted.market.global.common.code.TradeStatusCode;
import lombok.Getter;

@Getter
public class TradeDto {

    private long tradeNo;

    private long productNo;

    private String productName;

    private TradeStatusCode status;

    public TradeDto(long tradeNo, long productNo, String productName, TradeStatusCode status) {
        this.tradeNo = tradeNo;
        this.productNo = productNo;
        this.productName = productName;
        this.status = status;
    }
}
