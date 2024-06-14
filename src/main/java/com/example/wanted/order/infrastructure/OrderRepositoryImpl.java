package com.example.wanted.order.infrastructure;

import com.example.wanted.order.domain.Order;
import com.example.wanted.order.domain.OrderStatus;
import com.example.wanted.order.service.port.OrderRepository;
import com.example.wanted.product.domain.Product;
import com.example.wanted.product.infrastructure.ProductEntity;
import com.example.wanted.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(OrderEntity.fromModel(order)).toModel();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderJpaRepository.findById(id).map(OrderEntity::toModel);
    }

    @Override
    public List<Order> findByUser(User user) {
        return orderJpaRepository
                .findByUserId(user.getId())
                .stream()
                .map(OrderEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByProductAndUser(Product product, User user) {
        return orderJpaRepository
                .findByUserIdOrProductId(user.getId(), product.getId())
                .stream()
                .map(OrderEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByProductAndOrderStatusIn(Product product, List<OrderStatus> status) {

        log.error("test");
        return orderJpaRepository
                .findByProductAndStatusIn(ProductEntity.fromModel(product), status)
                .stream()
                .map(OrderEntity::toModel)
                .collect(Collectors.toList());
    }

}
