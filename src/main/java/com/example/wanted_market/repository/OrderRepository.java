package com.example.wanted_market.repository;

import com.example.wanted_market.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByProductId(Long productId);

    @Query("SELECT o From Order o " +
            "WHERE o.buyer.id = :userId OR o.product.seller.id = :userId " +
            "ORDER BY o.createDate DESC")
    List<Order> findOrderByBuyerOrSellerOrderByCreateDateDesc(Long userId);
}
