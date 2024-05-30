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


    @PostMapping("/{id}/reserve")
    public ResponseDto<ProductOrderDto> reserve(@PathVariable int id, @AuthenticationPrincipal String userId){
        ProductOrderDto result = productOrderService.reserve(id, userId);
        return ResponseDto.success(result);
    }

    @GetMapping("/{id}/approve")
    public ResponseDto<Void> approve(@PathVariable int id, @AuthenticationPrincipal String userId){
        productOrderService.approve(id,userId);
        return ResponseDto.success();
    }

    @GetMapping("/history")
    public ResponseDto<HistoryResponse> history(@AuthenticationPrincipal String userId){
        HistoryResponse result = productOrderService.history(userId);
        return ResponseDto.success(result);
    }

}
