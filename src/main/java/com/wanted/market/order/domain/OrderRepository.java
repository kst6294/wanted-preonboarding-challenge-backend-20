package com.wanted.market.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByProductIdAndBuyerId(Long productId, Long buyerId);
}
