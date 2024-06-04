package com.wanted.demo.domain.statements.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellerReservationResponseDTO {
    private Long id;
    private String buyerEmail;
    private Long productId;
    private String name;
    private Long purchasePrice;
    private LocalDateTime purchaseDate;
    private boolean purchaseStatus;
    private boolean saleConfirmStatus;

    @Builder
    public SellerReservationResponseDTO(Long id, String buyerEmail,Long productId, String name, Long purchasePrice, LocalDateTime purchaseDate, boolean purchaseStatus, boolean saleConfirmStatus){
        this.id = id;
        this.buyerEmail = buyerEmail;
        this.productId = productId;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.purchaseStatus = purchaseStatus;
        this.saleConfirmStatus = saleConfirmStatus;
    }
}
