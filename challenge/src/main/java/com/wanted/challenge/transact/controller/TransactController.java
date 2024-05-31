package com.wanted.challenge.transact.controller;

import com.wanted.challenge.account.model.AccountDetail;
import com.wanted.challenge.transact.request.ApproveRequest;
import com.wanted.challenge.transact.request.ConfirmRequest;
import com.wanted.challenge.transact.service.TransactService;
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
@RequestMapping("/transacts")
public class TransactController {

    private final TransactService transactService;

    @PostMapping("/approve")
    public ResponseEntity<Void> purchaseApprove(@RequestBody @Valid ApproveRequest approveRequest,
                                                @AuthenticationPrincipal AccountDetail accountDetail) {

        transactService.approve(approveRequest.productId(), approveRequest.buyerId(), accountDetail.getAccountId());

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmPurchase(@RequestBody @Valid ConfirmRequest confirmRequest,
                                                @AuthenticationPrincipal AccountDetail accountDetail) {

        transactService.confirm(confirmRequest.productId(), accountDetail.getAccountId());

        return ResponseEntity.ok()
                .build();
    }
}
