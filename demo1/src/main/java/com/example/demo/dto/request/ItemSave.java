package com.example.demo.dto.request;

import com.example.demo.entity.ItemState;

public record ItemSave(
        String name,
        int price,
        int quantity,
        ItemState itemState

) {
}
