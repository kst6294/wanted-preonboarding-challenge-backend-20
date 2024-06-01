package com.wanted.challenge.domain.transactionhistory.controller;

import com.wanted.challenge.domain.transactionhistory.dto.response.MyPurchaseHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MyBuyerReservationHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MySellerReservationHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.service.TransactionHistoryService;
import com.wanted.challenge.global.api.ApiResponse;
import com.wanted.challenge.global.auth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

        List<MyPurchaseHistoryResponseDTO> purchaseHistories = transactionHistoryService.getPurchaseHistories(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(purchaseHistories, "구매 기록 조회에 성공했습니다."));
    }

    // 현재 유저의 예약기록 전부 가져오기(구매자)
    @GetMapping("reservation/buyer")
    public ResponseEntity<ApiResponse<?>> getBuyerReservationHistories(@AuthUser Long userId) {

        List<MyBuyerReservationHistoryResponseDTO> reservationHistories = transactionHistoryService.getBuyerReservationHistories(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(reservationHistories, "예약 기록 조회에 성공했습니다.(구매자)"));
    }

    // 현재 유저의 예약기록 전부 가져오기(판매자)
    @GetMapping("reservation/seller")
    public ResponseEntity<ApiResponse<?>> getSellerReservationHistories(@AuthUser Long userId) {

        List<MySellerReservationHistoryResponseDTO> sellerReservationHistories = transactionHistoryService.getSellerReservationHistories(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(sellerReservationHistories, "예약 기록 조회에 성공했습니다.(판매자)"));
    }
}
