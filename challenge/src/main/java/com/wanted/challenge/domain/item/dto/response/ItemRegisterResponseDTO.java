package com.wanted.challenge.domain.item.dto.response;

import com.wanted.challenge.domain.item.entity.Item;
import com.wanted.challenge.domain.item.entity.SaleStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemRegisterResponseDTO {

    private Long id;

    private String name;

    private Long price;

    private SaleStatus saleStatus;

    private Integer quantity;

    private Long userId;

    private LocalDateTime createDate;

    @Builder
    public ItemRegisterResponseDTO(Long id, String name, Long price,  SaleStatus saleStatus, Integer quantity, Long userId, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.saleStatus = saleStatus;
        this.quantity = quantity;
        this.userId = userId;
        this.createDate = createDate;
    }

    public static ItemRegisterResponseDTO toDTO(Item item){
        return ItemRegisterResponseDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .saleStatus(item.getSaleStatus())
                .quantity(item.getQuantity())
                .userId(item.getMember().getId())
                .createDate(item.getCreatedDate()).build();
    }
}
