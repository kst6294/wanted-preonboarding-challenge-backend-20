package com.wanted.preonboarding.domain.purchase.controller;

import com.wanted.preonboarding.domain.product.dto.request.AddProductRequest;
import com.wanted.preonboarding.domain.purchase.dto.request.PurchaseRequest;
import com.wanted.preonboarding.domain.purchase.service.PurchaseService;
import com.wanted.preonboarding.global.auth.AuthUser;
import com.wanted.preonboarding.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<?> purchase(@AuthUser Long userId, @RequestBody @Valid PurchaseRequest purchaseRequest) {
        purchaseService.purchase(userId, purchaseRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccess("상품 구매가 완료되었습니다."));
    }

    @PostMapping("/accept")
    public ResponseEntity<?> accept(@AuthUser Long userId, @RequestBody @Valid PurchaseRequest purchaseRequest) {
        purchaseService.accept(userId, purchaseRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.createSuccess("주문 판매승인이 완료되었습니다."));
    }


    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@AuthUser Long userId, @RequestBody @Valid PurchaseRequest purchaseRequest) {
        purchaseService.confirm(userId, purchaseRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.createSuccess("주문 구매확정이 완료되었습니다."));
    }
}
