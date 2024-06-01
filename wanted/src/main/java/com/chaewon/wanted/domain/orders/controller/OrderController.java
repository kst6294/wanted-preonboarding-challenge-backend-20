package com.chaewon.wanted.domain.orders.controller;

import com.chaewon.wanted.common.ResponseDto;
import com.chaewon.wanted.domain.orders.dto.OrderRequestDto;
import com.chaewon.wanted.domain.orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseDto> createOrder(@AuthenticationPrincipal User user,
                                                   @Valid @RequestBody OrderRequestDto orderRequestDto) {
        orderService.createOrder(user.getUsername(), orderRequestDto);
        return ResponseDto.of(HttpStatus.OK, "제품 거래를 시작했습니다.");
    }
}
