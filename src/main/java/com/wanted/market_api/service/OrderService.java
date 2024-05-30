package com.wanted.market_api.service;

import com.wanted.market_api.constant.CustomerIdentity;
import com.wanted.market_api.dto.ApiResponse;
import com.wanted.market_api.dto.response.order.PagingOrderResponseDto;
import org.springframework.data.domain.Pageable;


public interface OrderService {
    ApiResponse purchase(Long productId, Long buyerId);
    PagingOrderResponseDto getOrders(Pageable pageable, CustomerIdentity customerIdentity, Long memberId);
    ApiResponse confirmOrder(Long orderId, Long memberId);
}
