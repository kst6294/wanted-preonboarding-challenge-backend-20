package com.sunyesle.wanted_market.repository;

import com.sunyesle.wanted_market.entity.Offer;
import com.sunyesle.wanted_market.enums.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    boolean existsByProductIdAndBuyerIdAndStatus(Long productId, Long buyerId, OfferStatus offerStatus);

    List<Offer> findByProductIdAndStatus(Long productId, OfferStatus offerStatus);

    List<Offer> findByBuyerId(Long memberId);

    List<Offer> findBySellerId(Long memberId);
}
