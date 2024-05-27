package com.wanted.preonboarding.module.order.mapper;


import com.wanted.preonboarding.module.order.core.BaseOrderContext;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl implements OrderMapper{

    @Override
    public Order toOrder(Product product, Users buyer) {
        Order order = Order.builder()
                .orderStatus(OrderStatus.ORDERED)
                .product(product)
                .seller(product.getSeller())
                .build();
        order.setBuyer(buyer);
        return order;
    }

    @Override
    public BaseOrderContext toOrderContext(Order order) {
        return BaseOrderContext.builder()
                .orderId(order.getId())
                .buyer(order.getBuyer().getEmail())
                .seller(order.getSeller().getEmail())
                .productId(order.getProduct().getId())
                .orderStatus(order.getOrderStatus())
                .build();
    }

}
