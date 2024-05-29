package com.market.wanted.order.service;

import com.market.wanted.member.entity.Member;
import com.market.wanted.member.service.MemberService;
import com.market.wanted.order.dto.OrderDto;
import com.market.wanted.order.entity.Order;
import com.market.wanted.order.entity.OrderItem;
import com.market.wanted.order.entity.OrderStatus;
import com.market.wanted.order.repository.OrderRepository;
import com.market.wanted.product.dto.ProductDto;
import com.market.wanted.product.entity.Product;
import com.market.wanted.product.entity.ProductStatus;
import com.market.wanted.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final ProductService productService;

    @Transactional
    public OrderDto createOrder(Long memberId, Long productId) {
        Member findMember = memberService.findById(memberId);
        Product findProduct = productService.findById(productId);

        if(findProduct.getStatus() == ProductStatus.SALE) {
            OrderItem orderItem = new OrderItem(findProduct.getPrice(), findProduct);
            Order order = new Order(OrderStatus.RESERVATION, findMember, orderItem);
            findProduct.changeStatus(ProductStatus.RESERVATION);
            orderRepository.save(order);
            return new OrderDto(order.getId(),
                    findMember.getId(),
                    order.getOrderItem().getProduct().getBuyer().getId(),
                    order.getOrderItem().getProduct().getId(),
                    order.getOrderItem().getPrice(),
                    order.getOrderStatus());
        }
        return null;

    }

    @Transactional
    public void orderConfirm(Long buyerId, Long oderId) {
        Order findOrder = orderRepository.findById(oderId).orElse(null);
        if (!findOrder.getOrderItem().getProduct().getBuyer().getId().equals(buyerId)) {
            findOrder.confirm();
        }

    }
}
