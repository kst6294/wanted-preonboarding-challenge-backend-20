package com.wanted.market.product.infra;

import com.wanted.market.order.domain.OrderRepository;
import com.wanted.market.product.domain.DuplicateBuyerChecker;
import org.springframework.stereotype.Component;

@Component
public class DefaultDuplicateBuyerChecker implements DuplicateBuyerChecker {
    private final OrderRepository orderRepository;

    public DefaultDuplicateBuyerChecker(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean check(Long productId, Long buyerId) {
        return orderRepository.existsByProductIdAndBuyerId(productId, buyerId);
    }
}
