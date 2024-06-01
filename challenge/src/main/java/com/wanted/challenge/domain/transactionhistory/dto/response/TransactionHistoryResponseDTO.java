package com.wanted.challenge.domain.transactionhistory.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionHistoryResponseDTO {

    private Long id;
    private Long purchasePrice;
    private boolean saleConfirmStatus;
    private boolean purchaseConfirmStatus;
    private String buyerEmail;

    @Builder
    public TransactionHistoryResponseDTO(Long id, Long purchasePrice, boolean saleConfirmStatus, boolean purchaseConfirmStatus, String buyerEmail) {
        this.id = id;
        this.purchasePrice = purchasePrice;
        this.saleConfirmStatus = saleConfirmStatus;
        this.purchaseConfirmStatus = purchaseConfirmStatus;
        this.buyerEmail = buyerEmail;
    }
}
