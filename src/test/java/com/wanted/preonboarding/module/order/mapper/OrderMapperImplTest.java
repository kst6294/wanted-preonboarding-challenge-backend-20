package com.wanted.preonboarding.module.order.mapper;

import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.module.order.core.BaseOrderContext;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderMapperImplTest {

    @InjectMocks
    private OrderMapperImpl orderMapper;

    private Product product;
    private Users buyer;

    @BeforeEach
    void setUp() {
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        product = ProductFactory.generateProduct(createProduct);
        buyer = UsersModuleHelper.toUsersWithId();
    }


    @Test
    @DisplayName("Product와  Users를 Order로 매핑")
    void toOrder() {
        Order order = orderMapper.toOrder(product, buyer);

        assertThat(order.getProduct()).isEqualTo(product);
        assertThat(order.getBuyer()).isEqualTo(buyer);
        assertThat(order.getSeller()).isEqualTo(product.getSeller());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDERED);
    }

    @Test
    @DisplayName("Order를 BaseOrderContext로 매핑")
    void toOrderContext() {
        Order order = orderMapper.toOrder(product, buyer);
        BaseOrderContext orderContext = orderMapper.toOrderContext(order);

        assertThat(orderContext.getProductId()).isEqualTo(order.getProduct().getId());
        assertThat(orderContext.getBuyer()).isEqualTo(order.getBuyer().getEmail());
        assertThat(orderContext.getSeller()).isEqualTo(order.getSeller().getEmail());
        assertThat(orderContext.getOrderStatus()).isEqualTo(order.getOrderStatus());

    }

}