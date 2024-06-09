package com.sunyesle.wanted_market.entity;

import com.sunyesle.wanted_market.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long sellerId;

    private Long buyerId;

    private Integer price;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    public Offer(Long productId, Long sellerId, Long buyerId, Integer price, Integer quantity) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.price = price;
        this.quantity = quantity;
        this.status = OfferStatus.OPEN;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }
}
