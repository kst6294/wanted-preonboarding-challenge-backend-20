package com.wanted.challenge.domain.transactionhistory.controller;

import com.wanted.challenge.domain.item.dto.response.ItemDetailInfoResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MyTransactionHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.service.TransactionHistoryService;
import com.wanted.challenge.global.api.ApiResponse;
import com.wanted.challenge.global.auth.AuthUser;
import com.wanted.challenge.global.auth.OptionalAuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    // 현재 유저의 구매기록 가져오기
    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getPurchaseHistories(@AuthUser Long userId) {

        List<MyTransactionHistoryResponseDTO> purchaseHistories = transactionHistoryService.getPurchaseHistories(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(purchaseHistories, "구매 기록 조회에 성공했습니다."));
    }
}
