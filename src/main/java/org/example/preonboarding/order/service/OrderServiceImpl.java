package org.example.preonboarding.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.member.util.MemberUtil;
import org.example.preonboarding.order.model.domain.Order;
import org.example.preonboarding.order.model.payload.request.OrderCreateRequest;
import org.example.preonboarding.order.model.payload.response.OrderResponse;
import org.example.preonboarding.order.repository.OrderRepository;
import org.example.preonboarding.product.model.entity.Product;
import org.example.preonboarding.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final MemberUtil memberUtil;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime orderedAt) {
        String productNumbers = request.getProductNumber();
        Product product = productRepository.findByProductNumber(productNumbers);

        // TODO: 재고 차감
        deductStockQuantities(product);

        Member currentUser = memberUtil.getCurrentUser();
        Order order = Order.create(product, currentUser, orderedAt);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponse approveOrder(int orderId) {
        Order order = orderRepository.findById((long) orderId).orElseThrow(() ->
                new IllegalArgumentException("주문을 찾지 못했습니다.")
        );

        if(!Objects.equals(order.getSeller().getId(), memberUtil.getCurrentUser().getId())) {
            throw new IllegalArgumentException("주문의 판매자와 현재 사용자가 일치하지 않습니다.");
        }

        order.approveOrder();
        Order approvedOrder = orderRepository.save(order);

        return OrderResponse.of(approvedOrder);
    }

    private void deductStockQuantities(Product product) {

    }
}
