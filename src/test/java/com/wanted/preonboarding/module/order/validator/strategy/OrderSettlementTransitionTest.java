package com.wanted.preonboarding.module.order.validator.strategy;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.SecuritySupportTest;
import com.wanted.preonboarding.module.exception.order.InvalidOrderUpdateIssueException;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderSettlementTransitionTest extends SecuritySupportTest {

    @InjectMocks
    private OrderSettlementTransition orderCompletedTransition;

    private Order order;
    private Users buyer;
    private Users seller;

    @BeforeEach
    void setUp() {
        orderCompletedTransition = new OrderSettlementTransition();

        buyer = UsersModuleHelper.toUsersWithId();
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProduct);
        seller = product.getSeller();
        order = OrderModuleHelper.toOrderWithId(product, buyer);
        securityUserMockSetting(buyer);
        securityUserMockSetting(seller);

    }

    @Test
    @DisplayName("getHandledStatus 메서드가 COMPLETED 상태를 반환하는지 테스트")
    void getHandledStatus() {
        assertEquals(OrderStatus.COMPLETED, orderCompletedTransition.getHandledStatus());
    }

    @Test
    @DisplayName("canTransition 메서드가 SETTLEMENT 상태로 전환이 가능한지 테스트, 구매자만 업데이트 가능")
    void canTransitionToCompleted() {
        assertTrue(orderCompletedTransition.canTransition(OrderStatus.SETTLEMENT, order, buyer.getEmail()));
    }

    @Test
    @DisplayName("canTransition 메서드가 SETTLEMENT 상태로 전환이 가능한지 테스트, 판매자 업데이트시 실패")
    void invalid_canTransitionToCompleted() {
        assertThrows(
                InvalidOrderUpdateIssueException.class,
                () -> orderCompletedTransition.canTransition(OrderStatus.SETTLEMENT, order, seller.getEmail())
        );


    }

    @Test
    @DisplayName("canTransition 메서드가 SETTLEMENT 상태가 아닌 상태로 전환이 불가능한지 테스트")
    void cannotTransitionToNonCompleted() {
        assertFalse(orderCompletedTransition.canTransition(OrderStatus.ORDERED, order, buyer.getEmail()));
        assertFalse(orderCompletedTransition.canTransition(OrderStatus.COMPLETED, order, buyer.getEmail()));
    }

}