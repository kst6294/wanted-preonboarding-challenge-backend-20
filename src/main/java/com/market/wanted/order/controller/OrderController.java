package com.market.wanted.order.controller;

import com.market.wanted.member.service.MemberService;
import com.market.wanted.order.dto.CreateOrder;
import com.market.wanted.order.dto.OrderDto;
import com.market.wanted.order.service.OrderService;
import com.market.wanted.product.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final ProductService productService;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Validated CreateOrder order, HttpSession session) {
        Long memberId =(Long) session.getAttribute("memberId");

        OrderDto createOder = orderService.createOrder(memberId, order.getProductId());

        return ResponseEntity.ok(createOder);
    }


    //판매자 주문 승인
    @PatchMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable("orderId") Long oderId, HttpSession session) {
        Long buyerId = (Long) session.getAttribute("memberId");
        orderService.orderConfirm(buyerId, oderId);
        return ResponseEntity.ok("주문이 승인 되었습니다.");
    }


}
