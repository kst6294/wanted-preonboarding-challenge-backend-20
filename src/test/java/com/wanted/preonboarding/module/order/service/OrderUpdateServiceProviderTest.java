package com.wanted.preonboarding.module.order.service;

import com.wanted.preonboarding.module.exception.common.ProviderMappingException;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.validator.strategy.OrderStatusTransition;
import com.wanted.preonboarding.module.order.validator.strategy.OrderStatusTransitionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderUpdateServiceProviderTest {

    @Mock
    private OrderUpdateService orderCompletedUpdateService;

    @Mock
    private OrderUpdateService orderSettlementUpdateService;

    private OrderUpdateServiceProvider orderUpdateServiceProvider;


    @BeforeEach
    void setUp() {
        when(orderCompletedUpdateService.getOrderStatus()).thenReturn(OrderStatus.ORDERED);
        when(orderSettlementUpdateService.getOrderStatus()).thenReturn(OrderStatus.COMPLETED);

        orderUpdateServiceProvider = new OrderUpdateServiceProvider(
                List.of(orderCompletedUpdateService, orderSettlementUpdateService));
    }


    @Test
    void getOrderStatusTransition_validStatus() {
        assertEquals(orderCompletedUpdateService, orderUpdateServiceProvider.get(OrderStatus.ORDERED));
        assertEquals(orderSettlementUpdateService, orderUpdateServiceProvider.get(OrderStatus.COMPLETED));
    }

    @Test
    void getOrderStatusTransition_invalidStatus() {
        Exception exception = assertThrows(ProviderMappingException.class, () -> {
            orderUpdateServiceProvider.get(OrderStatus.SETTLEMENT);
        });

        String expectedMessage = "해당 프로바이더를 찾을 수 없습니다. 프로바이더 KEY: SETTLEMENT";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}