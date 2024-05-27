package com.wanted.preonboarding.module.order.repository;

import com.wanted.preonboarding.module.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
