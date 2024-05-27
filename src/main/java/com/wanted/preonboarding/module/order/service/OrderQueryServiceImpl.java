package com.wanted.preonboarding.module.order.service;


import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.mapper.OrderMapper;
import com.wanted.preonboarding.module.order.repository.OrderRepository;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.service.ProductFindService;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.user.service.UserFindService;
import com.wanted.preonboarding.module.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService{

    private final OrderRepository orderRepository;
    private final ProductFindService productFindService;
    private final UserFindService userFindService;
    private final OrderMapper orderMapper;

    public OrderContext doOrder(CreateOrder createOrder){
        Product product = productFindService.fetchProductEntity(createOrder.getProductId());
        doBooking(product);
        Order savedOrder = createOrder(product);
        return orderMapper.toOrderContext(savedOrder);
    }


    private Order createOrder(Product product){
        Users buyer = userFindService.fetchUserEntity(SecurityUtils.currentUserEmail());
        Order order = orderMapper.toOrder(product, buyer);
        return orderRepository.save(order);
    }


    private void doBooking(Product product){
        product.doBooking();
    }

}
