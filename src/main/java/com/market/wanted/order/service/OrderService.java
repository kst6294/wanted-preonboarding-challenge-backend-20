package com.market.wanted.order.service;

import com.market.wanted.member.entity.Member;
import com.market.wanted.member.service.MemberService;
import com.market.wanted.order.dto.OrderDto;
import com.market.wanted.order.dto.ResponseOrder;
import com.market.wanted.order.entity.Order;
import com.market.wanted.order.entity.OrderItem;
import com.market.wanted.order.entity.OrderStatus;
import com.market.wanted.order.repository.OrderFindRepository;
import com.market.wanted.order.repository.OrderRepository;
import com.market.wanted.product.entity.Product;
import com.market.wanted.product.entity.ProductStatus;
import com.market.wanted.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final ProductService productService;
    private final OrderFindRepository orderFindRepository;

    @Transactional
    public OrderDto createOrder(String username, Long productId) {
        Member buyer = memberService.findByUsername(username);
        Product findProduct = productService.findById(productId);

        if(findProduct.getStatus() == ProductStatus.SALE) {
            Order order = new Order(OrderStatus.RESERVATION, buyer, findProduct.getSeller());
            findProduct.changeStatus(ProductStatus.RESERVATION);
            orderRepository.save(order);
            buyer.addOrder(order);
            OrderItem orderItem = OrderItem.builder().price(findProduct.getPrice())
                    .product(findProduct)
                    .build();
           order.addOrderItem(orderItem);
            return new OrderDto(order.getId(),
                    buyer.getId(),
                    findProduct.getSeller().getId(),
                    findProduct.getId(),
                    findProduct.getPrice(),
                    findProduct.getProductName(),
                    order.getOrderStatus());
        }
        return null;

    }

    @Transactional
    public void orderConfirm(String username, Long oderId) {
        Order findOrder = orderRepository.findById(oderId).orElse(null);
        if (findOrder.getSeller().getUsername().equals(username)) {
            findOrder.confirm();
        }

    }

    public OrderDto findDtoById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        return new OrderDto(order.getId(),
                order.getSeller().getId(),
                order.getBuyer().getId(),
                order.getOrderItem().getProduct().getId(),
                order.getOrderItem().getPrice(),
                order.getOrderItem().getProduct().getProductName(),
                order.getOrderStatus());
    }


    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<OrderDto> findAllBySellerEmail(String email) {
        return orderFindRepository.findAllBySellerEmail(email);
    }
    public List<OrderDto> findAllByBuyerEmail(String email) {
        return orderFindRepository.findAllByBuyerEmail(email);
    }

    public ResponseOrder findResponseById(Long orderId, String username) {
        Order order = orderRepository.findById(orderId).orElse(null);
        return ResponseOrder.builder()
                .orderId(order.getId())
                .sellerId(order.getSeller().getId())
                .buyerId(order.getBuyer().getId())
                .orderStatus(order.getOrderStatus())
                .productName(order.getOrderItem().getProduct().getProductName())
                .productId(order.getOrderItem().getProduct().getId())
                .isSeller(username.equals(order.getSeller().getUsername()))
                .orderDateTime(order.getCreateDate())
                .build();
    }

}
