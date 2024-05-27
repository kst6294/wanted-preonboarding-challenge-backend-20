package com.wanted.preonboarding.module.order.controller;


import com.wanted.preonboarding.module.common.payload.ApiResponse;
import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import com.wanted.preonboarding.module.order.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class OrderController {

    private final OrderQueryService orderQueryService;

    @PreAuthorize("hasAnyAuthority('NORMAL')")
    @PostMapping("/order")
    public ResponseEntity<ApiResponse<OrderContext>> createOrder(@RequestBody @Validated CreateOrder createOrder) {
        return ResponseEntity.ok(ApiResponse.success(orderQueryService.doOrder(createOrder)));
    }

}
