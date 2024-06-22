package org.example.wantedmarket.repository;

import org.example.wantedmarket.domain.Order;
import org.example.wantedmarket.status.OrderStatus;

import java.util.List;

public interface OrderRepository {

    List<Order> findAllByProductId(Long productId);

    List<Order> findAllByBuyerIdAndOrderStatus(Long buyerId, OrderStatus orderStatus);

    List<Order> findAllBySellerIdAndOrderStatus(Long sellerId, OrderStatus orderStatus);

    void save(Order order);

}
