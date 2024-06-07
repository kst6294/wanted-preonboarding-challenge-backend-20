package com.sunyesle.wanted_market.repository;

import com.sunyesle.wanted_market.entity.Offer;
import com.sunyesle.wanted_market.enums.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    boolean existsByProductIdAndBuyerIdAndStatus(Long productId, Long buyerId, OfferStatus offerStatus);

    boolean existsByProductIdAndStatusAndIdNot(Long id, OfferStatus offerStatus, Long offerId);
}
