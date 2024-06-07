package com.wanted.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SellDto {

    private Long product_id;
    private String email;
    private String name;
    private Long price;
    private Long stock_quantity;
    private String status;

    @QueryProjection
    public SellDto(Long product_id, String email, String name, Long price, Long stock_quantity, String status) {
        this.product_id = product_id;
        this.email = email;
        this.name = name;
        this.price = price;
        this.stock_quantity = stock_quantity;
        this.status = status;
    }
}
