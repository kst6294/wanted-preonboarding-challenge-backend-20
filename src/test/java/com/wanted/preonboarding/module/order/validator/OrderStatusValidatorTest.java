package com.wanted.preonboarding.module.order.validator;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.SecuritySupportTest;
import com.wanted.preonboarding.module.exception.common.ProviderMappingException;
import com.wanted.preonboarding.module.exception.order.InvalidUpdateOrderStatusException;
import com.wanted.preonboarding.module.exception.order.NotFoundOrderException;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import com.wanted.preonboarding.module.order.dto.UpdateOrder;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import com.wanted.preonboarding.module.order.validator.strategy.OrderCompletedTransition;
import com.wanted.preonboarding.module.order.validator.strategy.OrderSettlementTransition;
import com.wanted.preonboarding.module.order.validator.strategy.OrderStatusTransition;
import com.wanted.preonboarding.module.order.validator.strategy.OrderStatusTransitionProvider;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
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
class OrderStatusValidatorTest extends SecuritySupportTest {

    @InjectMocks
    private OrderStatusValidator orderStatusValidator;

    @Mock
    private OrderFindService orderFindService;
    @Mock
    private OrderStatusTransitionProvider orderStatusTransitionProvider;

    @Mock
    private OrderStatusTransition orderStatusTransition;


    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;


    private Order order;
    private Users buyer;
    private Users seller;

    @BeforeEach
    void setUp() {
        buyer = UsersModuleHelper.toUsersWithId();
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProduct);
        seller = product.getSeller();
        order = OrderModuleHelper.toOrderWithId(product, buyer);


        securityUserMockSetting(buyer);
        securityUserMockSetting(seller);

    }


    @Test
    @DisplayName("유효한 주문 상태 업데이트 - 판매 승인")
    void validOrderStatusUpdate() {

        UpdateOrder updateOrder = OrderModuleHelper.toUpdateOrder(order, OrderStatus.COMPLETED);

        when(orderFindService.fetchOrderEntity(updateOrder.getOrderId())).thenReturn(order);
        when(orderStatusTransitionProvider.get(order.getOrderStatus())).thenReturn(orderStatusTransition);
        when(orderStatusTransition.canTransition(any(OrderStatus.class), any(Order.class), anyString())).thenReturn(true);

        boolean result = orderStatusValidator.isValid(updateOrder, constraintValidatorContext);
        assertTrue(result);
    }

    @Test
    @DisplayName("유효하지 않은 주문 상태 업데이트 - 상태 전환 불가능")
    void invalidOrderStatusUpdate() {
        UpdateOrder updateOrder = OrderModuleHelper.toUpdateOrder(order, OrderStatus.SETTLEMENT);

        when(orderFindService.fetchOrderEntity(updateOrder.getOrderId())).thenReturn(order);
        when(orderStatusTransitionProvider.get(order.getOrderStatus())).thenReturn(orderStatusTransition);
        when(orderStatusTransition.canTransition(any(OrderStatus.class), any(Order.class), anyString())).thenReturn(false);

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);

        boolean result = orderStatusValidator.isValid(updateOrder, constraintValidatorContext);

        assertFalse(result);
        verify(orderFindService).fetchOrderEntity(updateOrder.getOrderId());
    }


    @Test
    @DisplayName("유효하지 않은 주문 상태 업데이트 - 주문 없음")
    void invalidOrderStatusUpdate_OrderNotFound() {
        UpdateOrder updateOrder = OrderModuleHelper.toUpdateOrder(order, OrderStatus.COMPLETED);

        when(orderFindService.fetchOrderEntity(anyLong())).thenThrow(new NotFoundOrderException(updateOrder.getOrderId()));

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);

        boolean result = orderStatusValidator.isValid(updateOrder, constraintValidatorContext);

        assertFalse(result);
        verify(orderFindService).fetchOrderEntity(updateOrder.getOrderId());
    }

    @Test
    @DisplayName("유효하지 않은 주문 상태 업데이트 - 프로바이더 없음")
    void invalidOrderStatusUpdate_ProviderNotFound() {
        UpdateOrder updateOrder = OrderModuleHelper.toUpdateOrder(order, OrderStatus.SETTLEMENT);


        when(orderFindService.fetchOrderEntity(updateOrder.getOrderId())).thenReturn(order);
        when(orderStatusTransitionProvider.get(order.getOrderStatus())).thenThrow(new ProviderMappingException(String.valueOf(order.getOrderStatus())));

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);

        boolean result = orderStatusValidator.isValid(updateOrder, constraintValidatorContext);

        assertFalse(result);
        verify(orderStatusTransitionProvider).get(order.getOrderStatus());
    }






}