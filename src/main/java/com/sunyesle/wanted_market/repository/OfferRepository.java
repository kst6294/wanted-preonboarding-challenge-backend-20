package com.sunyesle.wanted_market.repository;

import com.sunyesle.wanted_market.entity.Offer;
import com.sunyesle.wanted_market.enums.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    boolean existsByProductIdAndBuyerIdAndStatus(Long productId, Long buyerId, OfferStatus offerStatus);

    List<Offer> findByBuyerId(Long memberId);

    @Query("select o from Offer o join Product p on p.id = o.productId and p.memberId = :memberId")
    List<Offer> findReceivedOffers(Long memberId);
}
