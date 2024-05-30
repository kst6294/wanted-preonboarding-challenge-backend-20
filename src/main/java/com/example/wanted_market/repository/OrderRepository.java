package com.example.wanted_market.repository;

import com.example.wanted_market.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByProductId(Long productId);
}
