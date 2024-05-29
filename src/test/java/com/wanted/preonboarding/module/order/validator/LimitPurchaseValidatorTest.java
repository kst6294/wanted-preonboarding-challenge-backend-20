package com.wanted.preonboarding.module.order.validator;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LimitPurchaseValidatorTest {

    @InjectMocks
    LimitPurchaseValidator limitPurchaseValidator;

    @Mock
    private OrderFindService orderFindService;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;

    @Test
    @DisplayName("구매한적 없을때")
    void hasPurchaseHistory() {
        // given
        when(orderFindService.hasPurchaseHistory(anyLong())).thenReturn(false);
        // when
        boolean result = limitPurchaseValidator.isValid(OrderModuleHelper.toCreateOrder(), constraintValidatorContext);
        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("구매한적 있을때")
    void hasPurchaseHistory_no() {
        // given
        when(orderFindService.hasPurchaseHistory(anyLong())).thenReturn(true);

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);

        // when
        boolean result = limitPurchaseValidator.isValid(OrderModuleHelper.toCreateOrder(), constraintValidatorContext);

        // then
        assertFalse(result);
        verify(constraintValidatorContext).disableDefaultConstraintViolation();
        verify(constraintValidatorContext).buildConstraintViolationWithTemplate(anyString());
    }


}