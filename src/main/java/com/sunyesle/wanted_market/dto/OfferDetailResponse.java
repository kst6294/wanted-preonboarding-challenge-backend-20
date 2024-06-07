package com.sunyesle.wanted_market.dto;

import com.sunyesle.wanted_market.entity.Offer;
import com.sunyesle.wanted_market.enums.OfferStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OfferDetailResponse {
    private final Long id;
    private final Long productId;
    private final Long sellerId;
    private final Long buyerId;
    private final Integer price;
    private final Integer quantity;
    private final OfferStatus status;

    @Builder
    private OfferDetailResponse(Long id, Long productId, Long sellerId, Long buyerId, Integer price, Integer quantity, OfferStatus status) {
        this.id = id;
        this.productId = productId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public static OfferDetailResponse of(Offer product) {
        return OfferDetailResponse.builder()
                .id(product.getId())
                .productId(product.getProductId())
                .sellerId(product.getSellerId())
                .buyerId(product.getBuyerId())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .status(product.getStatus()).build();
    }
}
