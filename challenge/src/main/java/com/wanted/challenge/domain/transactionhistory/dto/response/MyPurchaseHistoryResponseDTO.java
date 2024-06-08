package com.wanted.challenge.domain.transactionhistory.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPurchaseHistoryResponseDTO {

    private Long id;
    private String name;
    private Long purchasePrice;
    private LocalDateTime purchaseConfirmDate;

    @Builder
    public MyPurchaseHistoryResponseDTO(Long id, String name, Long purchasePrice, LocalDateTime purchaseConfirmDate) {
        this.id = id;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.purchaseConfirmDate = purchaseConfirmDate;
    }
}
