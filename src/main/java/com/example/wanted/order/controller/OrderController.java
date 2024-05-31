package com.example.wanted.order.controller;

import com.example.wanted.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v0/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
}
