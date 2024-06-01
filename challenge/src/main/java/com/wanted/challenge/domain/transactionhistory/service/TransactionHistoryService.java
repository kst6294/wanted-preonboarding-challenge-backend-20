package com.wanted.challenge.domain.transactionhistory.service;

import com.wanted.challenge.domain.transactionhistory.dto.response.MyPurchaseHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MyReservationHistoryResponseDTO;

import java.util.List;

public interface TransactionHistoryService {

    // 구매 기록 전부 가져오기
    List<MyPurchaseHistoryResponseDTO> getPurchaseHistories(Long id);

    // 예약 기록 전부 가져오기(구매자)
    List<MyReservationHistoryResponseDTO> getReservationHistories(Long id);

}
