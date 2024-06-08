package com.wanted.challenge.domain.item.dto.response;

import com.wanted.challenge.domain.item.entity.SaleStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemResponseDTO {

    private Long id;
    private Long userId;
    private String name;
    private Long price;
    private SaleStatus saleStatus;

    @Builder
    public ItemResponseDTO(Long id, Long userId, String name, Long price, SaleStatus saleStatus) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.saleStatus = saleStatus;
    }

}
