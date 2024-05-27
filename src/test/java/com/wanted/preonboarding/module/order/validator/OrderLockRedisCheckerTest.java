package com.wanted.preonboarding.module.order.validator;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.document.utils.RedisServiceTest;
import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrderLockRedisCheckerTest extends RedisServiceTest {

    @InjectMocks
    private OrderLockRedisChecker orderLockRedisChecker;


    @Test
    @DisplayName("상품에 대한 락이 성공적으로 설정되는 경우")
    void lock_success() {
        // given
        long productId = 1L;
        String key = RedisKey.ORDER_LOCK.generateKey(String.valueOf(productId));

        when(redisTemplate.hasKey(key)).thenReturn(false);

        // when
        boolean result = orderLockRedisChecker.lock(productId);

        // then
        assertTrue(result);
        verify(valueOperations).set(eq(key), eq("LOCKED"), any(Duration.class));
    }

    @Test
    @DisplayName("상품에 대한 락이 이미 설정되어 있는 경우")
    void lock_failure() {
        // given
        CreateOrder createOrder = OrderModuleHelper.toCreateOrder();
        String key = RedisKey.ORDER_LOCK.generateKey(String.valueOf(createOrder.getProductId()));

        when(redisTemplate.hasKey(key)).thenReturn(true);

        // when
        boolean result = orderLockRedisChecker.lock(createOrder.getProductId());

        // then
        assertFalse(result);
        verify(redisTemplate.opsForValue(), never()).set(anyString(), anyString(), any(Duration.class));
    }

}