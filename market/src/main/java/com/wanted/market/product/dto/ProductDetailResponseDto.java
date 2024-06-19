package com.wanted.market.product.dto;

import com.wanted.market.order.dto.OrderDetailResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductDetailResponseDto { // 거래내역 포함
    
    private ProductResponseDto product;
    private List<OrderDetailResponseDto> orderDetailList;


}
