package com.wanted.market.domain.trade.response;

import com.wanted.market.domain.trade.entity.Trade;
import com.wanted.market.global.common.code.ResponseCode;
import com.wanted.market.domain.trade.entity.TradeStatusCode;
import com.wanted.market.global.common.response.BaseResponse;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DetailResponse extends BaseResponse {

    private long tradeNo;

    private String productName;

    private long sellerMemberNo;

    private String sellerMemberId;

    private long buyerMemberNo;

    private String buyerMemberId;

    private TradeStatusCode tradeStatus;

    private LocalDateTime registerDate;

    private LocalDateTime updateDate;

    public DetailResponse(ResponseCode response, Trade trade) {
        super(response);
        this.tradeNo = trade.getTradeNo();
        this.productName = trade.getProduct().getName();
        this.sellerMemberNo = trade.getSeller().getMemberNo();
        this.sellerMemberId = trade.getSeller().getMemberId();
        this.buyerMemberNo = trade.getBuyer().getMemberNo();
        this.buyerMemberId = trade.getBuyer().getMemberId();
        this.tradeStatus = trade.getStatus();
        this.registerDate = trade.getRegisterDate();
        this.updateDate = trade.getUpdateDate();
    }

    public DetailResponse(ResponseCode response) {
        super(response);
    }
}
