package com.wanted.challenge.product.controller;

import com.wanted.challenge.account.model.AccountDetail;
import com.wanted.challenge.product.request.ApproveRequest;
import com.wanted.challenge.product.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/approve")
    public ResponseEntity<Void> purchaseApprove(@RequestBody @Valid ApproveRequest approveRequest,
                                                @AuthenticationPrincipal AccountDetail accountDetail) {

        purchaseService.approve(approveRequest.productId(), approveRequest.buyerId(), accountDetail.getAccountId());

        return ResponseEntity.ok()
                .build();
    }
}
