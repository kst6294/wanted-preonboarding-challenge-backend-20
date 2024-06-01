package com.example.wanted.order.controller;

import com.example.wanted.order.domain.OrderCreate;
import com.example.wanted.order.service.OrderService;
import com.example.wanted.order.service.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v0/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<OrderResponse> order(OrderCreate orderCreate, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity
                .ok()
                .body(orderService.order(orderCreate, userId));
    }

    @GetMapping("")
    public ResponseEntity<List<OrderResponse>> getByUserId(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity
                .ok()
                .body(orderService.getByUserId(userId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderResponse>> getByUserIdAndProductId(
            @PathVariable("productId") long productId,
            Principal principal
    ) {
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity
                .ok()
                .body(orderService.getByProductAndUser(userId, productId));
    }
}
