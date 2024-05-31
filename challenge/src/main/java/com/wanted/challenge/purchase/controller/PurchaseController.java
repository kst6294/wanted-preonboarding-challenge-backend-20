package com.wanted.challenge.purchase.controller;

import com.wanted.challenge.account.model.AccountDetail;
import com.wanted.challenge.purchase.request.ApproveRequest;
import com.wanted.challenge.purchase.request.ConfirmRequest;
import com.wanted.challenge.purchase.service.PurchaseService;
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

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmPurchase(@RequestBody @Valid ConfirmRequest confirmRequest,
                                                @AuthenticationPrincipal AccountDetail accountDetail) {

        purchaseService.confirm(confirmRequest.productId(), accountDetail.getAccountId());

        return ResponseEntity.ok()
                .build();
    }
}
