package com.example.demo.dto.request;

import com.example.demo.entity.ItemState;
import com.example.demo.entity.Member;

public record ItemBuy(
        Long id,

        ItemState itemState

) {
}
