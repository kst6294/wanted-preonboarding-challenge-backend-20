package com.wanted.market.order.controller;

import com.wanted.market.order.dto.OrderRequestDto;
import com.wanted.market.order.dto.OrderResponseDto;
import com.wanted.market.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('buyer')")
    public ResponseEntity<OrderResponseDto> order(@Valid @RequestBody OrderRequestDto orderRequestDto){
        OrderResponseDto newOrder = orderService.order(orderRequestDto);
        return ResponseEntity.ok(newOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrders(){
        List<OrderResponseDto> orderResponseDtos = orderService.getAll();
        return ResponseEntity.ok(orderResponseDtos);
    }
}
