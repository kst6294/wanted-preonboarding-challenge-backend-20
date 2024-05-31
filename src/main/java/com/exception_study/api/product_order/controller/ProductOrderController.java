package com.exception_study.api.product_order.controller;

import com.exception_study.api.product_order.dto.response.HistoryResponse;
import com.exception_study.global.dto.response.ResponseDto;
import com.exception_study.api.product_order.service.ProductOrderService;
import com.exception_study.api.product_order.dto.ProductOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class ProductOrderController {
    private final ProductOrderService productOrderService;


    @PutMapping("/{productId}/reserve")
    public ResponseDto<ProductOrderDto> reserve(@PathVariable Long productId, @AuthenticationPrincipal String userId){
        ProductOrderDto result = productOrderService.reserve(productId, userId);
        return ResponseDto.success(result);
    }

    @PutMapping("/{id}/approve")
    public ResponseDto<Void> approve(@PathVariable Long id, @AuthenticationPrincipal String userId){
        productOrderService.approve(id,userId);
        return ResponseDto.success();
    }

    @PutMapping("/{id}/confirm")
    public ResponseDto<Void> confirm(@PathVariable Long id, @AuthenticationPrincipal String userId){
        productOrderService.confirm(id,userId);
        return ResponseDto.success();
    }

    @GetMapping("/history")
    public ResponseDto<HistoryResponse> history(@AuthenticationPrincipal String userId){
        HistoryResponse result = productOrderService.history(userId);
        return ResponseDto.success(result);
    }

}
