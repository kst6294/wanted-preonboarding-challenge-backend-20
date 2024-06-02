package com.sunyesle.wanted_market.repository;

import com.sunyesle.wanted_market.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
