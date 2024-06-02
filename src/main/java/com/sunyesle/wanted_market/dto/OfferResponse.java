package com.sunyesle.wanted_market.dto;

import com.sunyesle.wanted_market.enums.OfferStatus;
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
