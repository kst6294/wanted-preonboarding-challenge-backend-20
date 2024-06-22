package org.example.wantedmarket.repository;

import org.example.wantedmarket.domain.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> findAllByProductId(Long productId);

}
