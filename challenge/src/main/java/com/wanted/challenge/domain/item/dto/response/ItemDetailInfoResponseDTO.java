package com.wanted.challenge.domain.item.dto.response;

import com.wanted.challenge.domain.item.entity.Item;
import com.wanted.challenge.domain.item.entity.SaleStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED
)
public class ItemDetailInfoResponseDTO {

    private Long id;
    private Long userId;
    private String name;
    private Long price;
    private Integer quantity;
    private SaleStatus saleStatus;

    @Builder
    public ItemDetailInfoResponseDTO(Long id, Long userId, String name, Long price, Integer quantity, SaleStatus saleStatus) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.saleStatus = saleStatus;
    }

    public static ItemDetailInfoResponseDTO toDTO(Item item) {
        return ItemDetailInfoResponseDTO.builder()
                .id(item.getId())
                .userId(item.getMember().getId())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .saleStatus(item.getSaleStatus()).build();
    }
}
