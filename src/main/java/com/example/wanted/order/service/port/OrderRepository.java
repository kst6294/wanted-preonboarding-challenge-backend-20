package com.example.wanted.order.service.port;

import com.example.wanted.order.domain.Order;
import com.example.wanted.product.domain.Product;
import com.example.wanted.user.domain.User;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findByUser(User user);
    List<Order> findByProductAndUser(Product product, User user);
}
