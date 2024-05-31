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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderResponse order(OrderCreate orderCreate, Long userId) {
        User buyer = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", userId)
        );

        Product product = productRepository.findById(orderCreate.getProductId()).orElseThrow(() ->
                new ResourceNotFoundException("Product", orderCreate.getProductId())
        );
        // 구매자 관련 오류로 수정
        if (product.checkSeller(buyer)) {
            throw new ResourceNotFoundException("Product", orderCreate.getProductId());
        }
        product.deductQuantity();
        product = productRepository.save(product);

        Order order = orderRepository.save(Order.from(buyer, product));
        return OrderResponse.from(order);
    }

    public OrderResponse approve(Long orderId, Long userId) {
        User buyer = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", userId)
        );

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Order", orderId)
        );
        order.approve();
        order = orderRepository.save(order);

        return OrderResponse.from(order);
    }

    public List<OrderResponse> getByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", userId)
        );

        return orderRepository.findByUser(user).stream().map(OrderResponse::from).collect(Collectors.toList());
    }
}
