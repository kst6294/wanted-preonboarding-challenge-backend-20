package com.example.wanted.order.service.port;

import com.example.wanted.order.domain.Order;
import com.example.wanted.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByUser(User user);
}
