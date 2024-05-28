package com.wanted.preonboarding.module.order.controller;


import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.common.payload.ApiResponse;
import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import com.wanted.preonboarding.module.order.dto.UpdateOrder;
import com.wanted.preonboarding.module.order.filter.OrderFilter;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import com.wanted.preonboarding.module.order.service.OrderUpdateService;
import com.wanted.preonboarding.module.order.service.OrderUpdateServiceProvider;
import com.wanted.preonboarding.module.order.service.strategy.OrderCompletedUpdateService;
import com.wanted.preonboarding.module.order.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class OrderController {

    private final OrderFindService orderFindService;
    private final OrderQueryService orderQueryService;
    private final OrderUpdateServiceProvider orderUpdateServiceProvider;


    @PreAuthorize("hasAnyAuthority('NORMAL')")
    @PostMapping("/order")
    public ResponseEntity<ApiResponse<OrderContext>> createOrder(@RequestBody @Validated CreateOrder createOrder) {
        return ResponseEntity.ok(ApiResponse.success(orderQueryService.doOrder(createOrder)));
    }

    @PreAuthorize("hasAnyAuthority('NORMAL')")
    @PatchMapping("/order")
    public ResponseEntity<ApiResponse<OrderContext>> updateOrder(@RequestBody @Validated UpdateOrder updateOrder) {
        OrderUpdateService orderUpdateService = orderUpdateServiceProvider.get(updateOrder.getOrderStatus());
        return ResponseEntity.ok(ApiResponse.success(orderUpdateService.updateOrder(updateOrder)));
    }


    @PreAuthorize("hasAnyAuthority('NORMAL')")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<List<DetailedOrderContext>>> getOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(ApiResponse.success(orderFindService.fetchOrderDetail(orderId)));
    }


    @PreAuthorize("hasAnyAuthority('NORMAL')")
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<CustomSlice<DetailedOrderContext>>> getOrders(@ModelAttribute OrderFilter filter, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(orderFindService.fetchOrderDetails(filter, pageable)));
    }


}
