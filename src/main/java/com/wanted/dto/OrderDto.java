package com.wanted.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long order_id;
    private Long member_id;
    private String status;
    private LocalDate orderDate;
    private Long count;
    private Long price;
    private Long TotalPrice;
}
