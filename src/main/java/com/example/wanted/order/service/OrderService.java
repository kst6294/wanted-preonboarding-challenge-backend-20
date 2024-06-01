package com.example.wanted.order.service;

import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.order.domain.Order;
import com.example.wanted.order.domain.OrderCreate;
import com.example.wanted.order.service.port.OrderRepository;
import com.example.wanted.order.service.response.OrderResponse;
import com.example.wanted.product.domain.Product;
import com.example.wanted.product.service.port.ProductRepository;
import com.example.wanted.user.domain.User;
import com.example.wanted.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



@Slf4j
@Builder
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderResponse order(OrderCreate orderCreate, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", userId)
        );

        Product product = productRepository.findById(orderCreate.getProductId()).orElseThrow(() ->
                new ResourceNotFoundException("Product", orderCreate.getProductId())
        );

        List<Order> checkOrder = orderRepository.findByProductAndUser(product, user);
        if(!checkOrder.isEmpty()) {
            throw new IllegalArgumentException("주문 이력이 있습니다.");
        }
        // 구매자 관련 오류로 수정
        product.deductQuantity();
        product = productRepository.save(product);

        Order order = orderRepository.save(Order.from(user, product));
        return OrderResponse.from(order);
    }

    public OrderResponse approve(Long orderId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", userId)
        );

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Order", orderId)
        );
        order.approve(user);
        order = orderRepository.save(order);

        return OrderResponse.from(order);
    }

    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order", id)
        );

        return OrderResponse.from(order);
    }

    public List<OrderResponse> getByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", userId)
        );

        return orderRepository.findByUser(user).stream().map(OrderResponse::from).collect(Collectors.toList());
    }

    public List<OrderResponse> getByProductAndUser(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", userId)
        );

        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product", productId)
        );

        return orderRepository
                .findByProductAndUser(product,user).stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }
}
