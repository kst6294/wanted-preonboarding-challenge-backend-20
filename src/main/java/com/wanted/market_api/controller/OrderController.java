package com.wanted.market_api.controller;

import com.wanted.market_api.constant.CustomerIdentity;
import com.wanted.market_api.constant.UserAction;
import com.wanted.market_api.dto.ApiResponse;
import com.wanted.market_api.dto.response.order.PagingOrderResponseDto;
import com.wanted.market_api.service.OrderService;
import com.wanted.market_api.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/v1/order")
    public ResponseEntity<ApiResponse> purchase(@RequestParam(value = "productId") Long productId) {
        Long buyerId = AuthUtil.checkAuth();
        return ResponseEntity.ok(orderService.purchase(productId, buyerId));
    }

    @GetMapping("/v1/order/list")
    public ResponseEntity<ApiResponse<PagingOrderResponseDto>> getOrders(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam(value = "customerIdentity") CustomerIdentity customerIdentity
            ) {
        Long memberId = AuthUtil.checkAuth();
        return ResponseEntity.ok(new ApiResponse<>(orderService.getOrders(pageable, customerIdentity, memberId)));
    }

    @PatchMapping("/v1/order/confirm-order")
    public ResponseEntity confirmOrder(
            @RequestParam(value = "orderId") Long orderId,
            @RequestParam(value = "action") UserAction userAction
    ) {
        Long memberId = AuthUtil.checkAuth();
        return ResponseEntity.ok(orderService.confirmOrder(orderId, memberId, userAction));
    }

    @PatchMapping("/v1/order/confirm-purchase")
    public ResponseEntity confirmPurchase(
            @RequestParam(value = "orderId") Long orderId,
            @RequestParam(value = "action") UserAction userAction
    ) {
        Long memberId = AuthUtil.checkAuth();
        return ResponseEntity.ok(orderService.confirmPurchase(orderId, memberId, userAction));
    }
}
