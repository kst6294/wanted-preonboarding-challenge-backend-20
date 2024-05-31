package com.wanted.challenge.product.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.model.Reservation;
import com.wanted.challenge.transact.model.TransactState;

public record PurchaseProductResponse(Long productId, Reservation reservation, String name, Integer price,
                                      TransactState transactState) {

    @QueryProjection
    public PurchaseProductResponse(Long productId, Reservation reservation, String name, Price price,
                                   TransactState transactState) {

        this(productId, reservation, name, price.value(), transactState);
    }
}
