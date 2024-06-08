package com.wantedmarket.Deal.dto;

import com.wantedmarket.Deal.domain.Deal;
import com.wantedmarket.Deal.type.DealStatus;
import com.wantedmarket.Item.dto.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DealDto {
    private Long id;
    private String buyerName;
    private String sellerName;
    private ItemDto.Response item;
    private DealStatus dealStatus;

    public static DealDto from(Deal deal) {
        return DealDto.builder()
                .id(deal.getId())
                .buyerName(deal.getBuyer().getUsername())
                .sellerName(deal.getSeller().getUsername())
                .item(ItemDto.Response.from(deal.getItem()))
                .dealStatus(deal.getDealStatus())
                .build();
    }
}
