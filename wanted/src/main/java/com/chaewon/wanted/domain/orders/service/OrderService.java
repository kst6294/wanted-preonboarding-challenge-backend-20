package com.chaewon.wanted.domain.orders.service;

import com.chaewon.wanted.domain.orders.dto.request.OrderRequestDto;
import com.chaewon.wanted.domain.orders.dto.response.BuyerPurchaseHistoryResponseDto;
import com.chaewon.wanted.domain.orders.dto.response.SalesApprovalListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    void createOrder(String email, OrderRequestDto orderRequestDto);
    Page<BuyerPurchaseHistoryResponseDto> getMyPurchasesConditional(String username, String orderStatus, Pageable pageable);
    Page<SalesApprovalListResponseDto> findMySales(String email, Pageable pageable);
    void approveOrder(String email, Long orderId);
    void confirmPurchase(String email, Long orderId);
}
