package com.sunyesle.wanted_market.entity;

import com.sunyesle.wanted_market.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long sellerId;

    private Long buyerId;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    public void setStatus(OfferStatus status) {
        this.status = status;
    }
}
