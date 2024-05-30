package com.wanted.market_api.dto.response.order;

import com.wanted.market_api.constant.OrderStatus;
import com.wanted.market_api.dto.response.product.ProductResponseDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class OrderResponseDto {
    private long id;
    private ProductResponseDto product;
    private long buyerId;
    private long sellerId;
    private OrderStatus orderStatus;
}
