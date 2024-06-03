package com.wanted.demo.domain.product.dto.response;

import com.wanted.demo.domain.statements.dto.response.StatementsHistoryResponseDTO;
import com.wanted.demo.domain.statements.entity.Statements;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPurchaseResponseDTO {

    private Long id;
    private Long userId;
    private Long productId;
    private Long purchasePrice;
    private boolean purchaseStatus;
    private boolean sellStatus;

    @Builder
    public ProductPurchaseResponseDTO(Long id, Long userId, Long productId, Long purchasePrice, boolean purchaseStatus, boolean sellStatus) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.purchasePrice = purchasePrice;
        this.purchaseStatus = purchaseStatus;
        this.sellStatus = sellStatus;

    }

    public static ProductPurchaseResponseDTO toDTO(Statements statements){
        return ProductPurchaseResponseDTO.builder()
                .id(statements.getId())
                .userId(statements.getUser().getId())
                .productId(statements.getProduct().getId())
                .purchasePrice(statements.getPrice())
                .purchaseStatus(statements.isPurchaseStatus())
                .sellStatus(statements.isSellStatus())
                .build();
    }


}
