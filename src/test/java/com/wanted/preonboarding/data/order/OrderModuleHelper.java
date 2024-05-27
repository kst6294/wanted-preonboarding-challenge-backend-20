package com.wanted.preonboarding.data.order;

import com.wanted.preonboarding.data.EasyRandomUtils;
import com.wanted.preonboarding.module.order.core.BaseOrderContext;
import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import com.wanted.preonboarding.module.order.dto.UpdateOrder;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.entity.OrderHistory;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import org.jeasy.random.EasyRandom;

import java.util.HashMap;
import java.util.Map;

public class OrderModuleHelper {

    public static CreateOrder toCreateOrder(){
        EasyRandom instance = EasyRandomUtils.getInstance();
        return instance.nextObject(CreateOrder.class);
    }

    public static CreateOrder toCreateOrder(Product product){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("productId", product.getId());
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(CreateOrder.class);
    }

    public static UpdateOrder toUpdateOrder(Order order, OrderStatus orderStatus){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("orderId", order.getId());
        stringObjectMap.put("orderStatus", orderStatus);
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(UpdateOrder.class);
    }


    public static Order toOrder(Product product, Users buyer){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("id", 0L);
        stringObjectMap.put("product", product);
        stringObjectMap.put("seller", product.getSeller());
        stringObjectMap.put("buyer", buyer);
        stringObjectMap.put("orderStatus", null);
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        Order order = instance.nextObject(Order.class);
        order.changeOrderStatus(OrderStatus.ORDERED);
        order.createProductSnapShot();
        return order;
    }

    public static Order toOrderWithId(Product product, Users buyer){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("product", product);
        stringObjectMap.put("seller", product.getSeller());
        stringObjectMap.put("buyer", buyer);
        stringObjectMap.put("orderStatus", null);
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        Order order = instance.nextObject(Order.class);
        order.changeOrderStatus(OrderStatus.ORDERED);
        order.createProductSnapShot();
        return order;
    }


    public static BaseOrderContext toBaseOrderContext(Order order, String buyer){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("productId", order.getProduct().getId());
        stringObjectMap.put("seller", order.getSeller().getEmail());
        stringObjectMap.put("buyer", buyer);
        stringObjectMap.put("orderStatus", order.getOrderStatus());

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(BaseOrderContext.class);
    }


}
