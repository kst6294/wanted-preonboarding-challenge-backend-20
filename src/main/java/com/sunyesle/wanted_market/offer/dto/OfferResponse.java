package com.sunyesle.wanted_market.offer.dto;

import com.sunyesle.wanted_market.global.enums.OfferStatus;
import lombok.Getter;

@Getter
public class OfferResponse {
    private final Long id;
    private final OfferStatus status;

    public OfferResponse(Long id, OfferStatus status) {
        this.id = id;
        this.status = status;
    }
}
