package com.wanted.challenge.domain.transactionhistory.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyBuyerReservationHistoryResponseDTO {

    private Long id;
    private String name;
    private Long purchasePrice;
    private LocalDateTime purchaseRequestDate;
    private boolean saleConfirmStatus;
    private boolean purchaseConfirmStatus;
    private Long itemId;

    @Builder
    public MyBuyerReservationHistoryResponseDTO(Long id, String name, Long purchasePrice, LocalDateTime purchaseRequestDate, boolean saleConfirmStatus, boolean purchaseConfirmStatus, Long itemId) {
        this.id = id;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.purchaseRequestDate = purchaseRequestDate;
        this.saleConfirmStatus = saleConfirmStatus;
        this.purchaseConfirmStatus = purchaseConfirmStatus;
        this.itemId = itemId;
    }
}
