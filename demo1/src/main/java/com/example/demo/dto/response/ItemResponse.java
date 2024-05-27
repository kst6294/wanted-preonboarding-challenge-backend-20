package com.example.demo.dto.response;

import com.example.demo.entity.Item;
import com.example.demo.entity.ItemState;

public record ItemResponse(
        Long id,
        String name,
        int price,
        ItemState itemState
) {

    public ItemResponse(Item item){
        this(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getItemState()
        );
    }
}
