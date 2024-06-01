package com.market.wanted.item;

import lombok.Data;

@Data
public class ItemAddDto {
    private String name;
    private Long price;
    private Long count;
}
