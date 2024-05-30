package com.wanted.challenge.product.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.model.PurchaseDetail;
import com.wanted.challenge.product.model.Reservation;

public record PurchaseProductResponse(Long productId, Reservation reservation, String name, Integer price,
                                      PurchaseDetail purchaseDetail) {

    @QueryProjection
    public PurchaseProductResponse(Long productId, Reservation reservation, String name, Price price,
                                   PurchaseDetail purchaseDetail) {

        this(productId, reservation, name, price.value(), purchaseDetail);
    }
}
