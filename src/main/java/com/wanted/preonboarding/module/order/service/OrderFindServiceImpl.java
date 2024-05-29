package com.wanted.preonboarding.module.order.service;


import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.exception.order.NotFoundOrderException;
import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import com.wanted.preonboarding.module.order.dto.SettlementProductCount;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.filter.OrderFilter;
import com.wanted.preonboarding.module.order.mapper.OrderSliceMapper;
import com.wanted.preonboarding.module.order.repository.OrderFindRepository;
import com.wanted.preonboarding.module.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderFindServiceImpl implements OrderFindService{

    private final OrderFindRepository orderFindRepository;
    private final OrderSliceMapper orderSliceMapper;

    @Override
    public List<DetailedOrderContext> fetchOrderDetail(long orderId) {
        String email = SecurityUtils.currentUserEmail();
        return orderFindRepository.fetchOrderDetail(orderId, email);
    }

    @Override
    public Order fetchOrderEntity(long orderId) {
        return orderFindRepository.fetchOrderEntity(orderId).orElseThrow(()-> new NotFoundOrderException(orderId));
    }

    @Override
    public boolean hasPurchaseHistory(long productId) {
        String email = SecurityUtils.currentUserEmail();
        return orderFindRepository.hasPurchaseHistory(productId, email);
    }

    @Override
    public Optional<SettlementProductCount> fetchSettlementProductCount(long productId) {
        return orderFindRepository.fetchSettlementProductCount(productId);
    }

    @Override
    public CustomSlice<DetailedOrderContext> fetchOrderDetails(OrderFilter filter, Pageable pageable) {
        String email = SecurityUtils.currentUserEmail();
        List<DetailedOrderContext> detailedOrderContexts = orderFindRepository.fetchOrderDetails(filter, email, pageable);
        return orderSliceMapper.toSlice(detailedOrderContexts, pageable, filter);
    }


}
