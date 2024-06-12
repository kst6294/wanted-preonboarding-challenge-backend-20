package org.example.wantedmarket.repository;

import org.example.wantedmarket.model.Order;
import org.example.wantedmarket.status.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByBuyerIdAndStatus(Long buyerId, OrderStatus status);
    List<Order> findAllBySellerIdAndStatus(Long sellerId, OrderStatus status);
    List<Order> findAllBySellerId(Long sellerId);
    List<Order> findAllByBuyerId(Long buyerId);
}
