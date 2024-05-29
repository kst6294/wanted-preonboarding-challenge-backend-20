package com.market.wanted.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrder {

    @NotNull(message = "상품 아이디가 비어있습니다.")
    @Min(value = 1, message = "상품 아이디는 1 이상입니다.")
    private Long productId;

}
