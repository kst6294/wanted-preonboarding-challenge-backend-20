package com.wanted.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PurchaseDto {

    private Long order_id;
    private String email;
    private String name;
    private Long count;
    private Long price;
    private Long totalPrice;
    private String status;

    @QueryProjection
    public PurchaseDto(Long order_id, String email, String name, Long count, Long price, Long totalPrice, String status) {
        this.order_id = order_id;
        this.email = email;
        this.name = name;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
