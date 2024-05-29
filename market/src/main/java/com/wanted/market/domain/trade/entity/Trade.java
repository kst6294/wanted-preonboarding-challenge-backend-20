package com.wanted.market.domain.trade.entity;

import com.wanted.market.domain.member.entity.Member;
import com.wanted.market.domain.product.entity.Product;
import com.wanted.market.global.common.code.TradeStatusCode;
import com.wanted.market.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Table(name = "trades")
@Getter
@Entity
@Builder
@AllArgsConstructor
public class Trade extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "number")
    private long tradeNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_number")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_number")
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_number")
    private Member buyer;

    @Enumerated(EnumType.STRING)
    private TradeStatusCode status;

    public Trade() {
    }

    public Trade(Product product, Member buyer) {
        this.product = product;
        this.seller = product.getSeller();
        this.buyer = buyer;
        this.status = TradeStatusCode.PENDING;

        buyer.getBuyerTrades().add(this);
        product.getSeller().getSellerTrades().add(this);
    }

    public void changeStatus(TradeStatusCode status) {
        this.status = status;
    }
}
