package com.wanted.demo.domain.statements.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatementsHistoryResponseDTO {
    private Long id;
    private Long price;
    private boolean purchaseStatus;
    private boolean sellStatus;

    @Builder
    public StatementsHistoryResponseDTO(Long id, Long price, boolean purchaseStatus, boolean sellStatus){
        this.id = id;
        this.price = price;
        this.purchaseStatus = purchaseStatus;
        this.sellStatus = sellStatus;
    }

}
