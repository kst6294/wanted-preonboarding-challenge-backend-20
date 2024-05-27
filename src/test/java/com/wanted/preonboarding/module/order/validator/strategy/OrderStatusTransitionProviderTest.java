package com.wanted.preonboarding.module.order.validator.strategy;

import com.wanted.preonboarding.module.exception.common.ProviderMappingException;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderStatusTransitionProviderTest {

    @Mock
    private OrderStatusTransition orderCompletedTransition;

    @Mock
    private OrderStatusTransition orderSettlementTransition;

    private OrderStatusTransitionProvider orderStatusTransitionProvider;

    @BeforeEach
    void setUp() {
        when(orderCompletedTransition.getHandledStatus()).thenReturn(OrderStatus.ORDERED);
        when(orderSettlementTransition.getHandledStatus()).thenReturn(OrderStatus.COMPLETED);

        orderStatusTransitionProvider = new OrderStatusTransitionProvider(
                List.of(orderCompletedTransition, orderSettlementTransition));
    }

    @Test
    void getOrderStatusTransition_validStatus() {
        assertEquals(orderCompletedTransition, orderStatusTransitionProvider.get(OrderStatus.ORDERED));
        assertEquals(orderSettlementTransition, orderStatusTransitionProvider.get(OrderStatus.COMPLETED));
    }

    @Test
    void getOrderStatusTransition_invalidStatus() {
        Exception exception = assertThrows(ProviderMappingException.class, () -> {
            orderStatusTransitionProvider.get(OrderStatus.SETTLEMENT);
        });

        String expectedMessage = "해당 프로바이더를 찾을 수 없습니다. 프로바이더 KEY: SETTLEMENT";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}