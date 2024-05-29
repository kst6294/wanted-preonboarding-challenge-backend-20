package com.wanted.preonboarding.module.order.service.strategy;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.module.order.core.BaseOrderContext;
import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.dto.SettlementProductCount;
import com.wanted.preonboarding.module.order.dto.UpdateOrder;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.entity.OrderHistory;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.mapper.OrderMapper;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.service.ProductQueryService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderSettlementUpdateServiceTest {

    @Mock
    private OrderFindService orderFindService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ProductQueryService productQueryService;

    @InjectMocks
    private OrderSettlementUpdateService orderSettlementUpdateService;

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
    @DisplayName("주문 상태 업데이트 - SETTLEMENT 상태로 변경, 상품 업데이트")
    void updateOrderStatusToCompleted_product_outOfStock() {
        // given
        UpdateOrder updateOrder = OrderModuleHelper.toUpdateOrder(order, OrderStatus.SETTLEMENT);
        order.changeOrderStatus(OrderStatus.SETTLEMENT);
        BaseOrderContext baseOrderContext = OrderModuleHelper.toBaseOrderContext(order, buyer.getEmail());

        when(orderFindService.fetchOrderEntity(updateOrder.getOrderId())).thenReturn(order);
        when(orderMapper.toOrderContext(order)).thenReturn(baseOrderContext);

        // when
        SettlementProductCount settlementProductCount = new SettlementProductCount(0, 0);
        when(orderFindService.fetchSettlementProductCount(order.getProduct().getId())).thenReturn(Optional.of(settlementProductCount));


        OrderContext result = orderSettlementUpdateService.updateOrder(updateOrder);

        // then
        assertNotNull(result);
        assertEquals(OrderStatus.SETTLEMENT, result.getOrderStatus());
        verify(orderFindService).fetchOrderEntity(updateOrder.getOrderId());
        verify(orderMapper).toOrderContext(order);
        Optional<OrderHistory> orderHistoryOpt = order.getOrderHistories().stream()
                .filter(orderHistory -> orderHistory.getOrderStatus()
                        .isSettlement())
                .findFirst();

        assertThat(orderHistoryOpt).isPresent();

        verify(productQueryService).outOfStock(order.getProduct().getId());
    }


    @Test
    @DisplayName("주문 상태 업데이트 - SETTLEMENT 상태로 변경, 상품 업데이트 X")
    void updateOrderStatusToCompleted_product_onStock() {
        // given
        UpdateOrder updateOrder = OrderModuleHelper.toUpdateOrder(order, OrderStatus.COMPLETED);
        order.changeOrderStatus(OrderStatus.COMPLETED);
        BaseOrderContext baseOrderContext = OrderModuleHelper.toBaseOrderContext(order, buyer.getEmail());

        when(orderFindService.fetchOrderEntity(updateOrder.getOrderId())).thenReturn(order);
        when(orderMapper.toOrderContext(order)).thenReturn(baseOrderContext);

        SettlementProductCount settlementProductCount = new SettlementProductCount(0, 1);
        when(orderFindService.fetchSettlementProductCount(order.getProduct().getId())).thenReturn(Optional.of(settlementProductCount));

        // when
        OrderContext result = orderSettlementUpdateService.updateOrder(updateOrder);

        // then
        assertNotNull(result);
        assertEquals(OrderStatus.COMPLETED, result.getOrderStatus());
        verify(orderFindService).fetchOrderEntity(updateOrder.getOrderId());
        verify(orderMapper).toOrderContext(order);

        Optional<OrderHistory> orderHistoryOpt = order.getOrderHistories().stream()
                .filter(orderHistory -> orderHistory.getOrderStatus().isCompleted())
                .findFirst();
        assertThat(orderHistoryOpt).isPresent();

        // Verify that productQueryService.outOfStock was not called
        verify(productQueryService, never()).outOfStock(order.getProduct().getId());
    }



}