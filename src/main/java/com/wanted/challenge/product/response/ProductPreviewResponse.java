package com.wanted.challenge.product.response;

import com.querydsl.core.annotations.QueryProjection;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.model.Reservation;

public record ProductPreviewResponse(String productName, int price, Reservation reservation) {

    @QueryProjection
    public ProductPreviewResponse(String productName, Price price, Reservation reservation) {
        this(productName, price.value(), reservation);
    }
}
