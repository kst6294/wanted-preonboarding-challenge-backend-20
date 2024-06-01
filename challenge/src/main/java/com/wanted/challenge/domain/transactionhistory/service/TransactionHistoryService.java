package com.wanted.challenge.domain.transactionhistory.service;

import com.wanted.challenge.domain.transactionhistory.dto.response.MyTransactionHistoryResponseDTO;

import java.util.List;

public interface TransactionHistoryService {

    List<MyTransactionHistoryResponseDTO> getPurchaseHistories(Long id);
}
