package com.wanted.preonboarding.module.order.service;

import com.wanted.preonboarding.data.order.OrderFactory;
import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.SecuritySupportTest;
import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import com.wanted.preonboarding.module.order.dto.SettlementProductCount;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.enums.UserRole;
import com.wanted.preonboarding.module.order.filter.OrderFilter;
import com.wanted.preonboarding.module.order.mapper.OrderSliceMapperImpl;
import com.wanted.preonboarding.module.order.repository.OrderFindRepositoryImpl;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.utils.SecurityUtils;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderFindServiceImplTest extends SecuritySupportTest {

    @InjectMocks
    private OrderFindServiceImpl orderFindService;
    @Mock
    private OrderFindRepositoryImpl orderFindRepository;

    @Spy
    private OrderSliceMapperImpl orderSliceMapperImpl;

    private Users buyer;
    private Users seller;

    @BeforeEach
    void setUp() {
        buyer = UsersModuleHelper.toUsersWithId();
        seller = UsersModuleHelper.toUsersWithId();
        securityUserMockSetting(buyer);
        securityUserMockSetting(seller);
    }



    @Test
    @DisplayName("주문 단일 조회 - 성공")
    void fetchOrderEntity_success() {
        Product product = ProductModuleHelper.toProduct();
        product.setSeller(seller);
        Order order = OrderModuleHelper.toOrderWithId(product, buyer);

        when(orderFindRepository.fetchOrderEntity(order.getId())).thenReturn(Optional.of(order));

        Order findOrder = orderFindService.fetchOrderEntity(order.getId());

        assertThat(findOrder).isNotNull();
        assertThat(findOrder.getBuyer()).isNotNull();
        assertThat(findOrder.getSeller()).isNotNull();
    }


    @Test
    @DisplayName("거래내역 조회 - 성공")
    void fetchDetailedOrderContexts_snapshot_success() {
        Product product = ProductModuleHelper.toProduct();
        product.setSeller(seller);
        Order order = OrderModuleHelper.toOrderWithId(product, buyer);
        List<DetailedOrderContext> detailedOrderContexts = OrderModuleHelper.toDetailedOrderContextsForSnapShot(order);

        when(orderFindRepository.fetchOrderDetail(anyLong(), anyString())).thenReturn(detailedOrderContexts);

        List<DetailedOrderContext> findDetailedOrderContexts = orderFindService.fetchOrderDetail(order.getId());

        assertThat(findDetailedOrderContexts).isNotNull();
        AssertionsForInterfaceTypes.assertThat(findDetailedOrderContexts).hasSize(3);
        assertThat(findDetailedOrderContexts.get(0).getOrderStatus()).isEqualTo(OrderStatus.ORDERED);
        assertThat(findDetailedOrderContexts.get(1).getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(findDetailedOrderContexts.get(2).getOrderStatus()).isEqualTo(OrderStatus.SETTLEMENT);
    }


    @Test
    @DisplayName("거래내역 조회 - 성공")
    void fetchDetailedOrderContexts_success() {
        OrderFilter filter = new OrderFilter(null , null, null);
        Pageable pageable = PageRequest.of(0, 5);
        List<DetailedOrderContext> detailedOrderContexts = OrderFactory.generateDetailedOrderContext(buyer, seller,6);

        when(orderFindRepository.fetchOrderDetails(any(OrderFilter.class),anyString(), any(Pageable.class))).thenReturn(detailedOrderContexts);

        CustomSlice<DetailedOrderContext> customSlice = orderSliceMapperImpl.toSlice(detailedOrderContexts, pageable, filter);

        doReturn(customSlice).when(orderSliceMapperImpl).toSlice(anyList(), any(Pageable.class), any(ItemFilter.class));

        // when
        CustomSlice<DetailedOrderContext> results = orderFindService.fetchOrderDetails(filter, pageable);

        assertThat(results).isNotNull();
        AssertionsForInterfaceTypes.assertThat(results.getContent()).hasSize(5);
        assertThat(results.getContent().get(0).getProductName()).isEqualTo(customSlice.getContent().get(0).getProductName());
    }


    @Test
    @DisplayName("거래 내역 조회 구매자 - 성공")
    void fetchDetailedOrderContextsByBuyer_success() {
        OrderFilter filter = new OrderFilter(null , UserRole.BUYER, null);
        Pageable pageable = PageRequest.of(0, 5);
        List<DetailedOrderContext> detailedOrderContexts = OrderFactory.generateDetailedOrderContext(buyer, seller,6);

        when(orderFindRepository.fetchOrderDetails(any(OrderFilter.class),anyString(), any(Pageable.class))).thenReturn(detailedOrderContexts);

        CustomSlice<DetailedOrderContext> customSlice = orderSliceMapperImpl.toSlice(detailedOrderContexts, pageable, filter);

        doReturn(customSlice).when(orderSliceMapperImpl).toSlice(anyList(), any(Pageable.class), any(ItemFilter.class));

        // when
        CustomSlice<DetailedOrderContext> results = orderFindService.fetchOrderDetails(filter, pageable);


        assertThat(results).isNotNull();
        AssertionsForInterfaceTypes.assertThat(results.getContent()).hasSize(5);
        assertThat(results.getContent().get(0).getProductName()).isEqualTo(customSlice.getContent().get(0).getProductName());
    }


    @Test
    @DisplayName("거래 내역 조회 판매자 - 성공")
    void fetchDetailedOrderContextsBySeller_success() {
        OrderFilter filter = new OrderFilter(null , UserRole.SELLER, null);
        Pageable pageable = PageRequest.of(0, 5);
        List<DetailedOrderContext> detailedOrderContexts = OrderFactory.generateDetailedOrderContext(buyer, seller,6);

        when(orderFindRepository.fetchOrderDetails(any(OrderFilter.class),anyString(), any(Pageable.class))).thenReturn(detailedOrderContexts);

        CustomSlice<DetailedOrderContext> customSlice = orderSliceMapperImpl.toSlice(detailedOrderContexts, pageable, filter);

        doReturn(customSlice).when(orderSliceMapperImpl).toSlice(anyList(), any(Pageable.class), any(ItemFilter.class));

        // when
        CustomSlice<DetailedOrderContext> results = orderFindService.fetchOrderDetails(filter, pageable);


        assertThat(results).isNotNull();
        AssertionsForInterfaceTypes.assertThat(results.getContent()).hasSize(5);
        assertThat(results.getContent().get(0).getProductName()).isEqualTo(customSlice.getContent().get(0).getProductName());
    }



    @Test
    @DisplayName("구매이력 조회")
    void hasPurchaseHistory_success() {

        String email = buyer.getEmail();
        long productId = 1L;

        try (MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class)) {
            mockedStatic.when(SecurityUtils::currentUserEmail).thenReturn(email);
            when(orderFindRepository.hasPurchaseHistory(eq(productId), eq(email))).thenReturn(true);
            boolean b = orderFindService.hasPurchaseHistory(productId);
            assertTrue(b);
        }
    }

    @Test
    @DisplayName("구매확정 카운트 조회")
    void fetchSettlementProductCount() {
        when(orderFindRepository.fetchSettlementProductCount(anyLong())).thenReturn(Optional.empty());
        Optional<SettlementProductCount> settlementProductCount = orderFindService.fetchSettlementProductCount(anyLong());
        assertTrue(settlementProductCount.isEmpty());
    }


}