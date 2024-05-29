package com.example.wanted_market.service;

import com.example.wanted_market.domain.Order;
import com.example.wanted_market.domain.Product;
import com.example.wanted_market.domain.User;
import com.example.wanted_market.repository.OrderRepository;
import com.example.wanted_market.repository.ProductRepository;
import com.example.wanted_market.repository.UserRepository;
import com.example.wanted_market.type.EOrderStatus;
import com.example.wanted_market.type.EProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    @Transactional(readOnly = true)
    public Order getOrderById(Long userId) {
        return orderRepository.findById(userId)
                .orElseThrow(() -> { throw new IllegalArgumentException("해당 주문이 존재하지 않습니다."); });
    }

    // 구매
    @Transactional
    public void purchase(Long productId, Long userId) {
        Product product = productService.getProductById(productId);
        User user = productService.getUserById(userId);

        if(product.getSeller().getId().equals(user.getId()))
            throw new IllegalArgumentException("본인이 등록한 제품은 구매할 수 없습니다.");

        if(product.getStatus().equals(EProductStatus.IN_RESERVATION))
            throw new IllegalArgumentException("해당 제품은 현재 예약 중입니다.");
        if(product.getStatus().equals(EProductStatus.COMPLETED))
            throw new IllegalArgumentException("해당 제품은 이미 판매 완료되었습니다.");

        orderRepository.save(Order.builder()
                .product(product)
                .buyer(user)
                .status(EOrderStatus.IN_PROGRESS)
                .build());

        product.setStatus(EProductStatus.IN_RESERVATION);
    }

    // 판매 승인
    @Transactional
    public void approval(Long productId, Long orderId, Long userId) {
        Product product = productService.getProductById(productId);
        User user = productService.getUserById(userId);
        Order order = getOrderById(orderId);

        if(order.getStatus().equals(EOrderStatus.COMPLETED))
            throw new IllegalArgumentException("이미 완료된 주문입니다.");
        if(!product.getSeller().getId().equals(user.getId()))
            throw new IllegalArgumentException("판매자만 판매 승인할 수 있습니다.");

        product.setStatus(EProductStatus.COMPLETED);
        order.setStatus(EOrderStatus.COMPLETED);
    }
}
