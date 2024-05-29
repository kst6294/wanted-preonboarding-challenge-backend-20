package com.wanted.preonboarding.module.order.validator;

import com.wanted.preonboarding.module.order.dto.CreateOrder;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LimitPurchaseValidator implements ConstraintValidator<ValidLimitPurchase, CreateOrder> {

    private final OrderFindService orderFindService;

    protected static final String HAS_PURCHASE_HISTORY_ERR_MESSAGE = "구매 이력이 존재합니다.";

    @Override
    public boolean isValid(CreateOrder createOrder, ConstraintValidatorContext context) {
        if(createOrder == null){
            return false;
        }

        boolean hasPurchaseHistory = orderFindService.hasPurchaseHistory(createOrder.getProductId());

        if (hasPurchaseHistory) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(HAS_PURCHASE_HISTORY_ERR_MESSAGE)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
