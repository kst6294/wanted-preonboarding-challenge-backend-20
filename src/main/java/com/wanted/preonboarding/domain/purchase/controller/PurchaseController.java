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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
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

}
