package com.wanted.preonboarding.module.order.repository;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.BaseFetchRepositoryTest;
import com.wanted.preonboarding.module.common.enums.OrderType;
import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import com.wanted.preonboarding.module.order.dto.SettlementProductCount;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.filter.OrderFilter;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.utils.SecurityUtils;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class OrderFindRepositoryImplTest extends BaseFetchRepositoryTest {

    @Autowired
    OrderFindRepositoryImpl orderFindRepository;

    private Order order;
    private MockedStatic<SecurityUtils> mockedSecurityUtils;

    @BeforeEach
    void setUp() {
        if (mockedSecurityUtils != null) {
            mockedSecurityUtils.close();
        }
        mockedSecurityUtils = Mockito.mockStatic(SecurityUtils.class);
    }

    @AfterEach
    void tearDown() {
        mockedSecurityUtils.close();
    }


    @Test
    @DisplayName("Order Entity 조회")
    void fetchOrderEntity() {
        saveOrder();
        assertThat(order.getId()).isNotNull();
        Optional<Order> findOrder = orderFindRepository.fetchOrderEntity(order.getId());
        assertThat(findOrder).isPresent();
        assertThat(findOrder.get().getSeller()).isNotNull();
        assertThat(findOrder.get().getBuyer()).isNotNull();

    }



    @Test
    @DisplayName("DetailedOrderContext 조회 snapshot - 판매자가 조회")
    void fetchDetailedOrderContexts_bySeller_snapshot() {
        saveOrder();
        addOrderHistory(order);
        assertThat(order.getId()).isNotNull();
        when(SecurityUtils.currentUserEmail()).thenReturn(order.getBuyer().getEmail());

        List<DetailedOrderContext> detailedOrderContexts = orderFindRepository.fetchOrderDetail(order.getId(), order.getSeller().getEmail());
        assertThat(detailedOrderContexts).isNotNull();
        AssertionsForInterfaceTypes.assertThat(detailedOrderContexts).hasSize(3);
        assertThat(detailedOrderContexts.get(0).getOrderStatus()).isEqualTo(OrderStatus.ORDERED);
        assertThat(detailedOrderContexts.get(1).getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(detailedOrderContexts.get(2).getOrderStatus()).isEqualTo(OrderStatus.SETTLEMENT);

    }

    @Test
    @DisplayName("DetailedOrderContext snapshot 조회 - 구매자가 조회")
    void fetchDetailedOrderContexts_byBuyer_snapshot() {
        saveOrder();
        addOrderHistory(order);
        assertThat(order.getId()).isNotNull();

        when(SecurityUtils.currentUserEmail()).thenReturn(order.getBuyer().getEmail());


        List<DetailedOrderContext> detailedOrderContexts = orderFindRepository.fetchOrderDetail(order.getId(), order.getBuyer().getEmail());
        assertThat(detailedOrderContexts).isNotNull();
        AssertionsForInterfaceTypes.assertThat(detailedOrderContexts).hasSize(3);
        assertThat(detailedOrderContexts.get(0).getOrderStatus()).isEqualTo(OrderStatus.ORDERED);
        assertThat(detailedOrderContexts.get(1).getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(detailedOrderContexts.get(2).getOrderStatus()).isEqualTo(OrderStatus.SETTLEMENT);

    }

    @Test
    @DisplayName("DetailedOrderContext 조회 - 그냥 조회")
    void fetchDetailedOrderContexts_byNoIssuer() {
        Users buyer = UsersModuleHelper.toUsers();
        getEntityManager().persist(buyer);

        saveOrder(buyer);
        Pageable pageable = PageRequest.of(0, 3);
        OrderFilter orderFilter = new OrderFilter(null, null, OrderType.LATEST);

        when(SecurityUtils.currentUserEmail()).thenReturn(order.getBuyer().getEmail());

        assertThat(order.getId()).isNotNull();
        List<DetailedOrderContext> detailedOrderContexts = orderFindRepository.fetchOrderDetails(orderFilter, order.getBuyer().getEmail(), pageable);
        assertThat(detailedOrderContexts).isNotNull();
        AssertionsForInterfaceTypes.assertThat(detailedOrderContexts).hasSize(1);

    }


    @Test
    @DisplayName("DetailedOrderContext 조회 - 판매자가 조회")
    void fetchDetailedOrderContexts_bySeller() {
        Users buyer = UsersModuleHelper.toUsers();
        getEntityManager().persist(buyer);

        saveOrder(buyer);
        Pageable pageable = PageRequest.of(0, 3);
        OrderFilter orderFilter = new OrderFilter(null, null, OrderType.LATEST);

        when(SecurityUtils.currentUserEmail()).thenReturn(order.getBuyer().getEmail());

        assertThat(order.getId()).isNotNull();
        List<DetailedOrderContext> detailedOrderContexts = orderFindRepository.fetchOrderDetails(orderFilter, order.getSeller().getEmail(), pageable);
        assertThat(detailedOrderContexts).isNotNull();
        AssertionsForInterfaceTypes.assertThat(detailedOrderContexts).hasSize(1);
    }

    @Test
    @DisplayName("DetailedOrderContext 조회 - 구매자가 조회")
    void fetchDetailedOrderContexts_byBuyer() {
        Users buyer = UsersModuleHelper.toUsers();
        getEntityManager().persist(buyer);

        saveOrder(buyer);
        Pageable pageable = PageRequest.of(0, 3);
        OrderFilter orderFilter = new OrderFilter(null, null, OrderType.LATEST);

        when(SecurityUtils.currentUserEmail()).thenReturn(order.getBuyer().getEmail());


        assertThat(order.getId()).isNotNull();
        List<DetailedOrderContext> detailedOrderContexts = orderFindRepository.fetchOrderDetails(orderFilter, buyer.getEmail(), pageable);
        assertThat(detailedOrderContexts).isNotNull();
        AssertionsForInterfaceTypes.assertThat(detailedOrderContexts).hasSize(1);
    }


    @Test
    @DisplayName("구매이력 조회")
    void hasPurchaseHistory() {
        Users buyer = UsersModuleHelper.toUsers();
        getEntityManager().persist(buyer);
        saveOrder(buyer);

        when(SecurityUtils.currentUserEmail()).thenReturn(order.getBuyer().getEmail());

        assertThat(order.getId()).isNotNull();
        boolean b = orderFindRepository.hasPurchaseHistory(order.getProduct().getId(), buyer.getEmail());
        assertTrue(b);

    }


    @Test
    @DisplayName("재고 카운트 및 제품 구매 확정 단계 조회 - 판매중 상태")
    void fetchSettlementProductCount_onStock() {
        Users buyer = UsersModuleHelper.toUsers();
        getEntityManager().persist(buyer);

        saveOrder(buyer, 2);
        Product product = order.getProduct();

        addOrderHistory(order);

        Optional<SettlementProductCount> settlementProductCount = orderFindRepository.fetchSettlementProductCount(product.getId());

        assertEquals(ProductStatus.ON_STOCK, product.getProductStatus());
        assertThat(settlementProductCount).isEmpty();

    }

    @Test
    @DisplayName("재고 카운트 및 제품 구매 확정 단계 조회 - 예약 상태")
    void fetchSettlementProductCount_booking() {
        Users buyer = UsersModuleHelper.toUsers();
        getEntityManager().persist(buyer);

        saveOrder(buyer, 1);
        Product product = order.getProduct();

        Optional<SettlementProductCount> settlementProductCountOpt = orderFindRepository.fetchSettlementProductCount(product.getId());


        assertEquals(ProductStatus.BOOKING, product.getProductStatus());
        assertThat(settlementProductCountOpt).isPresent();
        SettlementProductCount settlementProductCount = settlementProductCountOpt.get();
        assertEquals(0, settlementProductCount.getQuantity());
        assertEquals(1, settlementProductCount.getOrderedOrCompletedProductCount());

    }


    @Test
    @DisplayName("재고 카운트 및 제품 구매 확정 단계 조회 - 완료 상태")
    void fetchSettlementProductCount_outOfStock() {
        Users buyer = UsersModuleHelper.toUsers();
        getEntityManager().persist(buyer);

        saveOrder(buyer, 1);
        Product product = order.getProduct();
        addOrderHistory(order);

        Optional<SettlementProductCount> settlementProductCountOpt = orderFindRepository.fetchSettlementProductCount(product.getId());

        assertEquals(ProductStatus.BOOKING, product.getProductStatus());
        assertThat(settlementProductCountOpt).isEmpty();
    }




    private void saveOrder() {
        Users buyer = UsersModuleHelper.toUsers();
        getEntityManager().persist(buyer);

        Users seller = UsersModuleHelper.toUsers();
        getEntityManager().persist(seller);

        Product product = ProductModuleHelper.toProduct();
        product.setSeller(seller);
        getEntityManager().persist(product);

        order = OrderModuleHelper.toOrder(product, buyer);
        getEntityManager().persist(order);
        flushAndClear();
    }


    private void saveOrder(Users buyer) {
        getEntityManager().persist(buyer);

        Users seller = UsersModuleHelper.toUsers();
        getEntityManager().persist(seller);

        Product product = ProductModuleHelper.toProduct();
        product.setSeller(seller);
        getEntityManager().persist(product);

        order = OrderModuleHelper.toOrder(product, buyer);
        getEntityManager().persist(order);
        flushAndClear();
    }


    private void saveOrder(Users buyer, int quantity) {
        getEntityManager().persist(buyer);

        Users seller = UsersModuleHelper.toUsers();
        getEntityManager().persist(seller);

        Product product = ProductModuleHelper.toProduct(quantity);
        product.setSeller(seller);
        getEntityManager().persist(product);

        order = OrderModuleHelper.toOrder(product, buyer);
        getEntityManager().persist(order);
        product.doBooking();
        flushAndClear();
    }


    private void addOrderHistory(Order order){
        order.changeOrderStatus(OrderStatus.COMPLETED);
        order.changeOrderStatus(OrderStatus.SETTLEMENT);
        getEntityManager().merge(order);
        flushAndClear();
    }


}