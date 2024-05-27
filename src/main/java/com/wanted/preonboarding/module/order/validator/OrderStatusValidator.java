package com.wanted.preonboarding.module.order.validator;

import com.wanted.preonboarding.module.exception.common.ProviderMappingException;
import com.wanted.preonboarding.module.exception.order.InvalidOrderUpdateIssueException;
import com.wanted.preonboarding.module.exception.order.InvalidUpdateOrderStatusException;
import com.wanted.preonboarding.module.exception.order.NotFoundOrderException;
import com.wanted.preonboarding.module.order.dto.UpdateOrder;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import com.wanted.preonboarding.module.order.validator.strategy.OrderStatusTransition;
import com.wanted.preonboarding.module.order.validator.strategy.OrderStatusTransitionProvider;
import com.wanted.preonboarding.module.utils.SecurityUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderStatusValidator implements ConstraintValidator<ValidOrderStatus, UpdateOrder> {

    private final static String ORDER_FIELD = "orderId";
    private final OrderFindService orderFindService;
    private final OrderStatusTransitionProvider orderStatusTransitionProvider;

    @Override
    public boolean isValid(UpdateOrder updateOrder, ConstraintValidatorContext context) {
        if(updateOrder == null){
            return false;
        }

        try {
            Order order = orderFindService.fetchOrderEntity(updateOrder.getOrderId());
            String currentUserEmail = SecurityUtils.currentUserEmail();


            OrderStatusTransition orderStatusTransition = orderStatusTransitionProvider.get(order.getOrderStatus());

            if (!orderStatusTransition.canTransition(updateOrder.getOrderStatus(), order, currentUserEmail)) {
                throw new InvalidUpdateOrderStatusException(order.getId(), order.getOrderStatus(), updateOrder.getOrderStatus());
            }

            return true;
        } catch (NotFoundOrderException | InvalidUpdateOrderStatusException | InvalidOrderUpdateIssueException | ProviderMappingException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(ORDER_FIELD)
                    .addConstraintViolation();
            return false;
        }
    }

}
