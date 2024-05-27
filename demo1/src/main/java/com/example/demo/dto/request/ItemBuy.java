package com.example.demo.dto.request;

import com.example.demo.entity.ItemState;

public record ItemBuy(
        Long id,
        ItemState itemState

) {
}
