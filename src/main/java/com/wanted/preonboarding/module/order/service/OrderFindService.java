package com.wanted.preonboarding.module.order.service;

import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import com.wanted.preonboarding.module.order.dto.SettlementProductCount;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.filter.OrderFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderFindService {

    List<DetailedOrderContext> fetchOrderDetail(long orderId);

    CustomSlice<DetailedOrderContext> fetchOrderDetails(OrderFilter filter, Pageable pageable);

    Order fetchOrderEntity(long orderId);

    boolean hasPurchaseHistory(long productId);

    Optional<SettlementProductCount> fetchSettlementProductCount(long productId);

}
