package com.wanted.challenge.domain.transactionhistory.service;

import com.wanted.challenge.domain.transactionhistory.dto.response.MyBuyerReservationHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MyPurchaseHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MySellerReservationHistoryResponseDTO;

import java.util.List;

public interface TransactionHistoryService {

    // 구매 기록 전부 가져오기
    List<MyPurchaseHistoryResponseDTO> getPurchaseHistories(Long id);

    // 예약 기록 전부 가져오기(구매자)
    List<MyBuyerReservationHistoryResponseDTO> getBuyerReservationHistories(Long id);

    // 예약 기록 전부 가져오기(판매자)
    List<MySellerReservationHistoryResponseDTO> getSellerReservationHistories(Long id);

    // 판매 승인
    void saleConfirm(Long userId, Long historyId);

    // 구매 승인
    void purchaseConfirm(Long userId, Long historyId);

}
