package org.example.wantedmarket.repository.jpa;

import org.example.wantedmarket.domain.Order;
import org.example.wantedmarket.status.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByProductId(Long productId);
    List<Order> findAllBySellerIdOrBuyerId(Long sellerId, Long buyerId);
    List<Order> findAllByBuyerIdAndOrderStatus(Long buyerId, OrderStatus orderStatus);
    List<Order> findAllBySellerIdAndOrderStatus(Long sellerId, OrderStatus orderStatus);
    Boolean existsByBuyerId(Long buyerId);

}
