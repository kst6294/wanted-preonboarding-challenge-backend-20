package com.wanted.preonboarding.backend20.domain.order.repository;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.order.domain.Order;
import com.wanted.preonboarding.backend20.domain.order.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findBySellerAndBuyer(Member seller, Member buyer, Pageable pageable);
    Page<Order> findByBuyerAndBuyerOrderStatus(Member buyer, OrderStatus buyerOrderStatus, Pageable pageable);
}
