package com.wantedmarket.Item.dto;

import com.wantedmarket.Item.domain.Item;
import com.wantedmarket.Item.type.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private String itemName;
    private Long price;

    @NoArgsConstructor
    public static class Request extends ItemDto {
    }

    @SuperBuilder
    @Getter
    public static class Response extends ItemDto {
        private Long id;
        private ItemStatus status;
        private String sellerName;

        public static Response from(Item item) {
            return Response.builder()
                    .id(item.getId())
                    .itemName(item.getItemName())
                    .price(item.getPrice())
                    .status(item.getItemStatus())
                    .sellerName(item.getSeller().getUsername())
                    .build();
        }
    }
}
