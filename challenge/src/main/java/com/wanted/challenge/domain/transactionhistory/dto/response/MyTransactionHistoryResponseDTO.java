package com.wanted.challenge.domain.transactionhistory.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyTransactionHistoryResponseDTO {

    private Long id;
    private String name;
    private Long purchasePrice;
    private boolean saleConfirmStatus;
    private boolean purchaseConfirmStatus;

    @Builder
    public MyTransactionHistoryResponseDTO(Long id, String name, Long purchasePrice, boolean saleConfirmStatus, boolean purchaseConfirmStatus) {
        this.id = id;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.saleConfirmStatus = saleConfirmStatus;
        this.purchaseConfirmStatus = purchaseConfirmStatus;
    }
}
