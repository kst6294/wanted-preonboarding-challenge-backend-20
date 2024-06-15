package com.sunyesle.wanted_market.offer;

import com.sunyesle.wanted_market.global.enums.OfferStatus;
import com.sunyesle.wanted_market.global.exception.ErrorCodeException;
import com.sunyesle.wanted_market.global.exception.OfferErrorCode;
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

    private Long buyerId;

    private Integer price;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    public Offer(Long productId, Long buyerId, Integer price, Integer quantity) {
        this.productId = productId;
        this.buyerId = buyerId;
        this.price = price;
        this.quantity = quantity;
        this.status = OfferStatus.OPEN;
    }

    public void accept() {
        validateStatus(OfferStatus.OPEN);
        this.status = OfferStatus.ACCEPTED;
    }

    public void decline() {
        validateStatus(OfferStatus.OPEN);
        this.status = OfferStatus.DECLINED;
    }

    public void confirm() {
        validateStatus(OfferStatus.ACCEPTED);
        this.status = OfferStatus.CONFIRMED;
    }

    private void validateStatus(OfferStatus offerStatus) {
        if (this.status != offerStatus) {
            throw new ErrorCodeException(OfferErrorCode.INVALID_OFFER_STATUS);
        }
    }
}
