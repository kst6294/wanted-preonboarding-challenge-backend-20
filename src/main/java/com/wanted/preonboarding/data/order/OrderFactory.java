package com.wanted.preonboarding.data.order;

import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;

import java.util.ArrayList;
import java.util.List;

public class OrderFactory {

    public static List<DetailedOrderContext> generateDetailedOrderContext(Users buyer, int size) {
        List<DetailedOrderContext> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(generateDetailedOrderContext(buyer));
        }

        return data;
    }

    public static List<DetailedOrderContext> generateDetailedOrderContext(Users buyer, Users seller, int size) {
        List<DetailedOrderContext> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(generateDetailedOrderContext(buyer, seller));
        }

        return data;
    }



    public static DetailedOrderContext generateDetailedOrderContext(Users buyer) {
        CreateProduct createProductWithUsers = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductModuleHelper.toProductWithId(createProductWithUsers);
        Order order = OrderModuleHelper.toOrderWithId(product, buyer);
        return OrderModuleHelper.toDetailedOrderContext(order);
    }

    public static DetailedOrderContext generateDetailedOrderContext(Users buyer, Users seller) {

        CreateProduct createProductWithUsers = ProductModuleHelper.toCreateProduct(seller);
        Product product = ProductModuleHelper.toProductWithId(createProductWithUsers, seller);
        Order order = OrderModuleHelper.toOrderWithId(product, buyer);
        return OrderModuleHelper.toDetailedOrderContext(order);
    }

}
