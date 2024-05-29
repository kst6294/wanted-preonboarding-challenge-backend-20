package com.wanted.preonboarding.module.order.repository;

import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import com.wanted.preonboarding.module.order.dto.SettlementProductCount;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.filter.OrderFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderFindRepository {

    List<DetailedOrderContext> fetchOrderDetail(long orderId, String email);
    List<DetailedOrderContext> fetchOrderDetails(OrderFilter filter, String email, Pageable pageable);
    Optional<Order> fetchOrderEntity(long orderId);

    boolean hasPurchaseHistory(long productId, String email);
    Optional<SettlementProductCount> fetchSettlementProductCount(long productId);

}
