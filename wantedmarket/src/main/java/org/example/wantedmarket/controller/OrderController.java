package org.example.wantedmarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.order.OrderCreateDto;
import org.example.wantedmarket.service.OrderService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /* 제품 주문 */
    @PostMapping
    public OrderCreateDto.Response orderProduct(Long userId, @RequestBody OrderCreateDto.Request request) {
        return orderService.orderProduct(userId, request);
    }

    /* 판매 승인 */
    @PatchMapping("/{orderId}/approval")
    public void approveProductOrder(@PathVariable Long orderId) {
        orderService.approveProductOrder(orderId);
    }

}
