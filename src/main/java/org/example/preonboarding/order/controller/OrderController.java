package org.example.preonboarding.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.preonboarding.common.enums.ResultCode;
import org.example.preonboarding.common.payload.Api;
import org.example.preonboarding.order.model.payload.request.OrderCreateRequest;
import org.example.preonboarding.order.model.payload.response.OrderResponse;
import org.example.preonboarding.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        LocalDateTime orderedAt = LocalDateTime.now();

        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<OrderResponse>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(orderService.createOrder(orderCreateRequest, orderedAt))
                        .build()
        );
    }

    // 판매자가 승인처리를 하는 것.
    @PostMapping("/approve/{orderId}")
    public ResponseEntity<?> approveOrder(@PathVariable("orderId") int orderId) {
        LocalDateTime orderedAt = LocalDateTime.now();
        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<OrderResponse>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(orderService.approveOrder(orderId))
                        .build()
        );
    }

}
