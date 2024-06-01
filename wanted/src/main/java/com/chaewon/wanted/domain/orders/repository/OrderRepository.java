package com.chaewon.wanted.domain.orders.repository;

import com.chaewon.wanted.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Long> {
}
