package com.wanted.preonboarding.module.order.validator;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderValidatorTest {

    @InjectMocks
    private OrderValidator orderValidator;

    @Mock
    private OrderLockChecker orderLockChecker;

    private CreateOrder createOrder;

    @BeforeEach
    void setUp() {
        createOrder = OrderModuleHelper.toCreateOrder();
    }

    @Test
    @DisplayName("최초 주문인 경우")
    void validOrder() {
        // given
        when(orderLockChecker.lock(anyLong())).thenReturn(true);

        // when
        boolean result = orderValidator.isValid(createOrder, null);

        // then
        assertTrue(result);
        verify(orderLockChecker).lock(createOrder.getProductId());

    }

    @Test
    @DisplayName("주문 중인 상태를 주문하려는 경우")
    void invalidOrder() {
        // given
        when(orderLockChecker.lock(anyLong())).thenReturn(false);

        // when
        boolean result = orderValidator.isValid(createOrder, null);

        // then
        assertFalse(result);
        verify(orderLockChecker).lock(createOrder.getProductId());
    }



}