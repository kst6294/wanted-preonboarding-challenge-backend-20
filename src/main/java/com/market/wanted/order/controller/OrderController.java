package com.market.wanted.order.controller;

import com.market.wanted.auth.dto.CustomUserDetails;
import com.market.wanted.order.dto.CreateOrder;
import com.market.wanted.order.dto.OrderDto;
import com.market.wanted.order.dto.ResponseOrder;
import com.market.wanted.order.entity.OrderStatus;
import com.market.wanted.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문생성
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Validated CreateOrder order, @AuthenticationPrincipal CustomUserDetails user) {

        OrderDto createOder = orderService.createOrder(user.getUsername(), order.getProductId());
        return ResponseEntity.ok(createOder);
    }

    //구매자 주문조회
    @GetMapping("/my")
    public ResponseEntity<List<OrderDto>> myOrders(@AuthenticationPrincipal CustomUserDetails user) {
        List<OrderDto> orderDtos = orderService.findAllByBuyerEmail(user.getUsername());
        return ResponseEntity.ok(orderDtos);
    }

    // 판매자 주문조회
    @GetMapping("/sales")
    public ResponseEntity<List<OrderDto>> orders(@AuthenticationPrincipal CustomUserDetails user) {
        List<OrderDto> orderDtos = orderService.findAllBySellerEmail(user.getUsername());
        return ResponseEntity.ok(orderDtos);
    }


    // 주문 상세내역 - 구매자
    @GetMapping("/my/{orderId}")
    public ResponseEntity<ResponseOrder> getMyOrder(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal CustomUserDetails user) {
        ResponseOrder responseOrder = orderService.findResponseById(orderId, user.getUsername());
        return ResponseEntity.ok(responseOrder);
    }

    // 주문 상세내역 - 판매자
    @GetMapping("/sales/{orderId}")
    public ResponseEntity<ResponseOrder> getSalesOrder(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal CustomUserDetails user) {
        ResponseOrder responseOrder = orderService.findResponseById(orderId, user.getUsername());
        return ResponseEntity.ok(responseOrder);
    }


    //판매자 주문 승인
    @PatchMapping("/sales/{orderId}")
    public ResponseEntity<String> orderConfirm(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal CustomUserDetails user) {
        OrderStatus orderStatus = orderService.findById(orderId).getOrderStatus();
        if (orderStatus == OrderStatus.RESERVATION) {
            orderService.orderConfirm(user.getUsername(), orderId);
            return ResponseEntity.ok("주문이 승인 되었습니다.");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 승인 완료된 주문입니다.");
        }
    }



}
