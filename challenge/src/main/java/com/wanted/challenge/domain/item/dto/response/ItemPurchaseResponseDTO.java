package com.wanted.challenge.domain.item.dto.response;

import com.wanted.challenge.domain.transactionhistory.entity.TransactionHistory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemPurchaseResponseDTO {

    private Long id;
    private boolean saleConfirmStatus;
    private boolean purchaseConfirmStatus;
    private Long purchasePrice;
    private Long itemId;
    private Long userId;

    @Builder
    public ItemPurchaseResponseDTO(Long id, boolean saleConfirmStatus, boolean purchaseConfirmStatus, Long purchasePrice, Long itemId, Long userId) {
        this.id = id;
        this.saleConfirmStatus = saleConfirmStatus;
        this.purchaseConfirmStatus = purchaseConfirmStatus;
        this.purchasePrice = purchasePrice;
        this.itemId = itemId;
        this.userId = userId;
    }

    public static ItemPurchaseResponseDTO toDTO(TransactionHistory transactionHistory){
        return ItemPurchaseResponseDTO.builder()
                .id(transactionHistory.getId())
                .saleConfirmStatus(transactionHistory.isSaleConfirmStatus())
                .purchaseConfirmStatus(transactionHistory.isPurchaseConfirmStatus())
                .purchasePrice(transactionHistory.getPurchasePrice())
                .itemId(transactionHistory.getItem().getId())
                .userId(transactionHistory.getMember().getId()).build();
    }
}
