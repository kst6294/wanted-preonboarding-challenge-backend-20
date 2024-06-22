package org.example.wantedmarket.repository;

import org.example.wantedmarket.domain.Order;
import org.example.wantedmarket.status.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long orderId);

    List<Order> findAllByProductId(Long productId);

    List<Order> findAllByBuyerIdAndOrderStatus(Long buyerId, OrderStatus orderStatus);

    List<Order> findAllBySellerIdAndOrderStatus(Long sellerId, OrderStatus orderStatus);

    List<Order> findAllBySellerIdOrBuyerId(Long sellerId, Long buyerId);

    boolean existsByBuyerId(Long buyerId);

    void save(Order order);

}
