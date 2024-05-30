package com.wanted.market_api.repository;

import com.wanted.market_api.constant.OrderStatus;
import com.wanted.market_api.entity.Order;
import com.wanted.market_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
            value = "SELECT o FROM Order o LEFT JOIN FETCH o.product p WHERE o.id = :id AND o.sellerId = :sellerId"
    )
    Optional<Order> findOrderAndProductByIdAndSellerId(Long id, Long sellerId);

    @Query(
            value = "SELECT o FROM Order o LEFT JOIN FETCH o.product p WHERE o.id = :id AND o.buyerId = :buyerId"
    )
    Optional<Order> findOrderAndProductByIdAndBuyerId(Long id, Long buyerId);
    List<Order> findAllByProduct(Product product);
    int countByOrderStatusIn(List<OrderStatus> orderStatusList);
}
