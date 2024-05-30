package com.wanted.market_api.service.impl;

import com.wanted.market_api.constant.*;
import com.wanted.market_api.dto.ApiResponse;
import com.wanted.market_api.dto.response.order.OrderResponseDto;
import com.wanted.market_api.dto.response.order.PagingOrderResponseDto;
import com.wanted.market_api.dto.response.product.ProductResponseDto;
import com.wanted.market_api.entity.Order;
import com.wanted.market_api.entity.Product;
import com.wanted.market_api.exception.BaseException;
import com.wanted.market_api.repository.OrderRepository;
import com.wanted.market_api.repository.OrderRepositoryCustom;
import com.wanted.market_api.service.OrderService;
import com.wanted.market_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderRepositoryCustom orderRepositoryCustom;

    private final ProductService productService;

    private final List<OrderStatus> ONGOING_ORDER_STATUS = List.of(
            OrderStatus.AWAITING_CONFIRMATION,
            OrderStatus.SELLING_CONFIRMED
    );

    @Transactional
    @Override
    public ApiResponse purchase(Long productId, Long buyerId) {
        Product product = productService.findWithMemberById(productId);
        if (product.getCount() == 0) {
            throw new BaseException(ErrorCode.PRODUCT_OUT_OF_INVENTORY);
        }
        long sellerId = product.getMember().getId();
        orderRepository.save(
                Order.builder()
                        .product(product)
                        .buyerId(buyerId)
                        .sellerId(sellerId)
                        .priceAtPurchase(product.getPrice())
                        .orderStatus(OrderStatus.AWAITING_CONFIRMATION)
                        .build()
        );
        product.decrementCount();
        return new ApiResponse();
    }

    @Override
    public PagingOrderResponseDto getOrders(Pageable pageable, CustomerIdentity customerIdentity, Long memberId) {
        Page<Order> pageOrders = orderRepositoryCustom.getOrders(pageable, customerIdentity, memberId);
        return PagingOrderResponseDto.builder()
                .totalPages(pageOrders.getTotalPages())
                .totalElements(pageOrders.getTotalElements())
                .pageNumber(pageOrders.getPageable().getPageNumber())
                .orders(pageOrders.getContent().stream().map(
                        order -> OrderResponseDto.builder()
                                .id(order.getId())
                                .product(ProductResponseDto.builder()
                                        .id(order.getProduct().getId())
                                        .name(order.getProduct().getName())
                                        .price(order.getProduct().getPrice())
                                        .productStatus(order.getProduct().getProductStatus())
                                        .build())
                                .buyerId(order.getBuyerId())
                                .sellerId(order.getSellerId())
                                .orderStatus(order.getOrderStatus())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    @Override
    public ApiResponse confirmOrder(Long orderId, Long memberId, UserAction userAction) {
        Order order = orderRepository.findOrderAndProductByIdAndSellerId(orderId, memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.ORDER_NOT_FOUND));
        if (userAction.equals(UserAction.CONFIRM)) {
            confirmOrder(order);
        } else {
            declineOrder(order);
        }
        return new ApiResponse();
    }

    @Transactional
    private void confirmOrder(Order order) {
        order.confirmOrder();
    }

    @Transactional
    private void declineOrder(Order order) {
        order.declineOrder();
        order.getProduct().incrementCount();
    }

    @Transactional
    @Override
    public ApiResponse confirmPurchase(Long orderId, Long memberId, UserAction userAction) {
        Order order = orderRepository.findOrderAndProductByIdAndBuyerId(orderId, memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.ORDER_NOT_FOUND));
        if (userAction.equals(UserAction.CONFIRM)) {
            confirmPurchase(order);
        } else {
            declinePurchase(order);
        }
        return new ApiResponse();
    }

    @Transactional
    private void confirmPurchase(Order order) {
        order.confirmPurchase();
        int onGoingConfirmationCount = orderRepository.countByOrderStatusIn(ONGOING_ORDER_STATUS);
        if (order.getProduct().getCount() == 0 && onGoingConfirmationCount == 0) {
            order.getProduct().completeSale();
        }
    }


    private void declinePurchase(Order order) {
        order.declinePurchase();
        order.getProduct().incrementCount();
    }
}
