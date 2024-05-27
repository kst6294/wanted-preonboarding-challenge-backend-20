package com.wanted.preonboarding.module.order.service.strategy;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.module.order.core.BaseOrderContext;
import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.dto.UpdateOrder;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.entity.OrderHistory;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.mapper.OrderMapper;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderCompletedUpdateServiceTest {

    @Mock
    private OrderFindService orderFindService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderCompletedUpdateService orderCompletedUpdateService;

    private Order order;
    private Users buyer;

    @BeforeEach
    void setUp() {
        buyer = UsersModuleHelper.toUsersWithId();
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProduct);
        order = OrderModuleHelper.toOrderWithId(product, buyer);
    }


    @Test
    @DisplayName("주문 상태 업데이트 - COMPLETED 상태로 변경")
    void updateOrderStatusToCompleted() {
        // given
        UpdateOrder updateOrder = OrderModuleHelper.toUpdateOrder(order, OrderStatus.COMPLETED);
        order.changeOrderStatus(OrderStatus.COMPLETED);
        BaseOrderContext baseOrderContext = OrderModuleHelper.toBaseOrderContext(order, buyer.getEmail());

        when(orderFindService.fetchOrderEntity(updateOrder.getOrderId())).thenReturn(order);
        when(orderMapper.toOrderContext(order)).thenReturn(baseOrderContext);

        // when
        OrderContext result = orderCompletedUpdateService.updateOrder(updateOrder);

        // then
        assertNotNull(result);
        assertEquals(OrderStatus.COMPLETED, result.getOrderStatus());
        verify(orderFindService).fetchOrderEntity(updateOrder.getOrderId());
        verify(orderMapper).toOrderContext(order);
        Optional<OrderHistory> orderHistoryOpt = order.getOrderHistories().stream().filter(orderHistory -> orderHistory.getOrderStatus().isCompleted()).findFirst();
        assertThat(orderHistoryOpt).isPresent();
    }

}