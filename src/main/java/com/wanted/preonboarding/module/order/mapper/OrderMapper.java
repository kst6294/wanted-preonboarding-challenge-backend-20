package com.wanted.preonboarding.module.order.mapper;

import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;

public interface OrderMapper {

    Order toOrder(Product product, Users buyer);

    OrderContext toOrderContext(Order order);
}
