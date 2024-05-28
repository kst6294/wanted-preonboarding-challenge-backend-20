package com.example.wanted_market.repository;

import com.example.wanted_market.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
