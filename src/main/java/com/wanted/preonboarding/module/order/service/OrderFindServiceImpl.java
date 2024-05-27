package com.wanted.preonboarding.module.order.service;


import com.wanted.preonboarding.module.exception.order.NotFoundOrderException;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.repository.OrderFindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderFindServiceImpl implements OrderFindService{

    private final OrderFindRepository orderFindRepository;

    @Override
    public Order fetchOrderEntity(long orderId) {
        return orderFindRepository.fetchOrderEntity(orderId).orElseThrow(()-> new NotFoundOrderException(orderId));
    }

}
