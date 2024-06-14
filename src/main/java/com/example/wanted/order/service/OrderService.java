package com.example.wanted.order.service;

import com.example.wanted.module.exception.AlreadyOrderException;
import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.order.domain.Order;
import com.example.wanted.order.domain.OrderCreate;
import com.example.wanted.order.domain.OrderStatus;
import com.example.wanted.order.infrastructure.OrderRepositoryImpl;
import com.example.wanted.order.service.port.OrderRepository;
import com.example.wanted.order.service.response.OrderResponse;
import com.example.wanted.product.domain.Product;
import com.example.wanted.product.domain.ProductSellingStatus;
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

    public synchronized OrderResponse order(OrderCreate orderCreate, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", userId)
        );

        Product product = productRepository.findById(orderCreate.getProductId()).orElseThrow(() ->
                new ResourceNotFoundException("Product", orderCreate.getProductId())
        );

        Order order = Order.from(user, product);
        //주문 이력 환인
        List<Order> checkOrder = orderRepository.findByProductAndUser(product, user);
        if(!checkOrder.isEmpty()) {
            throw new AlreadyOrderException("주문 이력이 있습니다.");
        }

        product.deductQuantity();
        product = productRepository.save(product);

        order = orderRepository.save(order);
        log.info("new order orderID: {}", order.getId());

        return OrderResponse.from(order);
    }

    public synchronized OrderResponse approve(Long orderId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", userId)
        );

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Order", orderId)
        );
        order.approve(user);
        order = orderRepository.save(order);
        log.info("order orderID: {} approve", order.getId());

        return OrderResponse.from(order);
    }

    public synchronized OrderResponse confirmation(Long orderId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", userId)
        );

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Order", orderId)
        );

        List<Order> notConfirmationOrders = orderRepository.findByProductAndOrderStatusIn(
                order.getProduct(), OrderStatus.notConfirmationStatus()
        );
        Product product = order.getProduct();
        if(
                notConfirmationOrders.size() == 1 &&
                notConfirmationOrders.get(0).getId().equals(order.getId()) &&
                product.getSellingStatus().equals(ProductSellingStatus.RESERVATION)
        ) {
            product.complete();
            product = productRepository.save(product);
        }

        order.complete(user, order.getProduct());
        order = orderRepository.save(order);
        log.info("order orderID: {} confirmation", order.getId());

        return OrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order", id)
        );

        return OrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", userId)
        );

        return orderRepository
                .findByUser(user).stream().map(OrderResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getByUserIdAndProductId(Long userId, Long productId) {
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
