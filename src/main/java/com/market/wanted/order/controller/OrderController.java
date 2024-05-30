package com.market.wanted.order.controller;

import com.market.wanted.order.dto.CreateOrder;
import com.market.wanted.order.dto.OrderDto;
import com.market.wanted.order.dto.ResponseOrder;
import com.market.wanted.order.entity.OrderStatus;
import com.market.wanted.order.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<OrderDto> createOrder(@Validated CreateOrder order, HttpSession session) {
        Long memberId =(Long) session.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.badRequest().body(null);
        }
        OrderDto createOder = orderService.createOrder(memberId, order.getProductId());
        return ResponseEntity.ok(createOder);
    }

    //구매자 주문조회
    @GetMapping("/my")
    public ResponseEntity<List<OrderDto>> myOrders(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        List<OrderDto> orderDtos = orderService.findAllByBuyerId(memberId);
        return ResponseEntity.ok(orderDtos);
    }

    // 판매자 주문조회
    @GetMapping("/sales")
    public ResponseEntity<List<OrderDto>> orders(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        List<OrderDto> orderDtos = orderService.findAllBySellerId(memberId);
        return ResponseEntity.ok(orderDtos);
    }


    // 주문 상세내역 - 구매자
    @GetMapping("/my/{orderId}")
    public ResponseEntity<ResponseOrder> getMyOrder(@PathVariable("orderId") Long orderId, HttpSession session) {
        Long memberId = (Long)session.getAttribute("memberId");
        ResponseOrder responseOrder = orderService.findResponseById(orderId, memberId);
        return ResponseEntity.ok(responseOrder);
    }

    // 주문 상세내역 - 판매자
    @GetMapping("/sales/{orderId}")
    public ResponseEntity<ResponseOrder> getSalesOrder(@PathVariable("orderId") Long orderId, HttpSession session) {
        Long memberId = (Long)session.getAttribute("memberId");
        ResponseOrder responseOrder = orderService.findResponseById(orderId, memberId);
        return ResponseEntity.ok(responseOrder);
    }


    //판매자 주문 승인
    @PatchMapping("/sales/{orderId}")
    public ResponseEntity<String> orderConfirm(@PathVariable("orderId") Long orderId, HttpSession session) {

        Long buyerId = (Long) session.getAttribute("memberId");
        if (buyerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        OrderStatus orderStatus = orderService.findById(orderId).getOrderStatus();
        if (orderStatus == OrderStatus.RESERVATION) {
            orderService.orderConfirm(buyerId, orderId);
            return ResponseEntity.ok("주문이 승인 되었습니다.");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 승인 완료된 주문입니다.");
        }
    }



}
