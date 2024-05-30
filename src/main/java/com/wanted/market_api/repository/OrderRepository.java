package com.wanted.market_api.repository;

import com.wanted.market_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndSellerId(Long id, Long memberId);
}
