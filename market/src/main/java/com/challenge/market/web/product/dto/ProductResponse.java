package com.challenge.market.web.product.dto;

import com.challenge.market.domain.product.constant.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private ProductStatus productStatus;
    private int price;

    




}
