package com.wanted.preonboarding.module.order.validator;

import com.wanted.preonboarding.module.order.dto.CreateOrder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderValidator implements ConstraintValidator<ValidOrder, CreateOrder> {

    private final OrderLockChecker orderLockChecker;

    @Override
    public boolean isValid(CreateOrder createOrder, ConstraintValidatorContext constraintValidatorContext) {
        if (createOrder == null) {
            return false;
        }
        return orderLockChecker.lock(createOrder.getProductId());
    }

}
