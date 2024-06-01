package com.market.wanted.item;

import lombok.Data;

import java.util.List;

@Data
public class ItemResponse {

    private List<ItemDetailResponse> list;
    private int count;

    public ItemResponse(List<Item> list){
        this.count = list.size();
        this.list = list.stream().map(i -> new ItemDetailResponse(i)).toList();
    }
}
