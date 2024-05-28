package com.wanted.preonboarding.backend20.domain.order.application;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.order.dto.OrderInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    void orderProduct(Long id, Member buyer);

    Page<OrderInfoDto> findOrderHistoryBetweenSellerAndBuyer(Long sellerId, Member buyer, Pageable pageable);

    void approveSellersOrder(Long orderId, Member seller);
    void confirmBuyersOrder(Long orderId, Member buyer);
}
