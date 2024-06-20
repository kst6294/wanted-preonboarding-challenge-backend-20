package com.wanted.market.order.repository;

import com.wanted.market.order.domain.Order;
import com.wanted.market.order.model.OrderStatus;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByProductId(Integer id);

    List<Order> findOrderByBuyerId(Integer buyerId);

    List<Order> findAllByBuyerIdAndOrderStatus(Integer memberId, OrderStatus orderStatus);

    List<Order> findAllBySellerIdAndOrderStatus(Integer memberId, OrderStatus orderStatus);
}
