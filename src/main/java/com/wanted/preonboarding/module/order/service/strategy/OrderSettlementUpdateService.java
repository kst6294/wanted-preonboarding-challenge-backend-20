package com.wanted.preonboarding.module.order.service.strategy;

import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.dto.UpdateOrder;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.mapper.OrderMapper;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import com.wanted.preonboarding.module.product.service.ProductFindService;
import com.wanted.preonboarding.module.product.service.ProductQueryService;
import org.springframework.stereotype.Service;

@Service
public class OrderSettlementUpdateService extends AbstractOrderUpdateService{

    private final ProductQueryService productQueryService;

    public OrderSettlementUpdateService(OrderFindService orderFindService, OrderMapper orderMapper, ProductQueryService productQueryService) {
        super(orderFindService, orderMapper);
        this.productQueryService = productQueryService;
    }

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.COMPLETED;
    }

    @Override
    public OrderContext updateOrder(UpdateOrder updateOrder) {
        OrderContext orderContext = super.updateOrder(updateOrder);

        if(hasAllSettlementStep(orderContext.getProductId())){
            productQueryService.outOfStock(orderContext.getProductId());
        }

        return orderContext;
    }

}
