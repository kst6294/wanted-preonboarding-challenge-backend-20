package com.sunyesle.wanted_market.entity;

import com.sunyesle.wanted_market.enums.OfferStatus;
import com.sunyesle.wanted_market.exception.ErrorCodeException;
import com.sunyesle.wanted_market.exception.OfferErrorCode;
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

    public void accept(Long memberId) {
        validateSeller(memberId);
        validateStatus(OfferStatus.OPEN);
        this.status = OfferStatus.ACCEPTED;
    }

    public void decline(Long memberId) {
        validateSeller(memberId);
        validateStatus(OfferStatus.OPEN);
        this.status = OfferStatus.DECLINED;
    }

    public void confirm(Long memberId) {
        validateBuyer(memberId);
        validateStatus(OfferStatus.ACCEPTED);
        this.status = OfferStatus.CONFIRMED;
    }

    private void validateSeller(Long memberId) {
        if (!memberId.equals(this.sellerId)) {
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEREE);
        }
    }

    private void validateBuyer(Long memberId) {
        if (!memberId.equals(this.buyerId)) {
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEROR);
        }
    }

    private void validateStatus(OfferStatus offerStatus) {
        if (this.status != offerStatus) {
            throw new ErrorCodeException(OfferErrorCode.INVALID_OFFER_STATUS);
        }
    }
}
