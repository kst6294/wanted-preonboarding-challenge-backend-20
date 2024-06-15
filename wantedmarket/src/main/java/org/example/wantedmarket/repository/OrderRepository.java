package org.example.wantedmarket.repository;

import org.example.wantedmarket.model.Order;
import org.example.wantedmarket.status.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByProductId(Long productId);
    List<Order> findAllBySellerIdOrBuyerId(Long sellerId, Long buyerId);
    List<Order> findAllByBuyerIdAndStatus(Long buyerId, OrderStatus status);
    List<Order> findAllBySellerIdAndStatus(Long sellerId, OrderStatus status);
    Boolean existsByBuyerId(Long buyerId);
}
