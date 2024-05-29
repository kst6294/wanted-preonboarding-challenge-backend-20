package com.wanted.preonboarding.module.order.service;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.SecuritySupportTest;
import com.wanted.preonboarding.module.order.core.BaseOrderContext;
import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.mapper.OrderMapper;
import com.wanted.preonboarding.module.order.repository.OrderRepository;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import com.wanted.preonboarding.module.product.service.ProductFindService;
import com.wanted.preonboarding.module.product.service.ProductQueryService;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.user.service.UserFindService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderQueryServiceImplTest extends SecuritySupportTest {

    @InjectMocks
    private OrderQueryServiceImpl orderQueryService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductQueryService productQueryService;

    @Mock
    private UserFindService userFindService;

    @Mock
    private OrderMapper orderMapper;

    private Users buyer;

    @BeforeEach
    void setUp() {
        buyer = UsersModuleHelper.toUsersWithId();
        securityUserMockSetting(buyer);

    }


    @Test
    @DisplayName("Order 생성")
    void createOrder() {
        CreateOrder createOrder = OrderModuleHelper.toCreateOrder();
        CreateProduct createProductWithUsers = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProductWithUsers);
        product.doBooking();
        Order order = OrderModuleHelper.toOrder(product, buyer);
        Order savedOrder = OrderModuleHelper.toOrderWithId(product, buyer);
        BaseOrderContext baseOrderContext = OrderModuleHelper.toBaseOrderContext(savedOrder, buyer.getEmail());

        when(productQueryService.doBooking(anyLong())).thenReturn(product);
        when(userFindService.fetchUserEntity(anyString())).thenReturn(buyer);
        when(orderMapper.toOrder(any(Product.class), any(Users.class))).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(orderMapper.toOrderContext(any(Order.class))).thenReturn(baseOrderContext);

        OrderContext orderContext = orderQueryService.doOrder(createOrder);


        assertThat(orderContext).isNotNull();
        assertThat(orderContext.getProductId()).isEqualTo(product.getId());
        assertThat(orderContext.getSeller()).isEqualTo(product.getSeller().getEmail());
        assertThat(orderContext.getBuyer()).isEqualTo(buyer.getEmail());
        assertThat(product.getProductStatus()).isEqualTo(ProductStatus.ON_STOCK);
        assertThat(savedOrder.getProductSnapShot()).isNotNull();
        assertThat(savedOrder.getOrderHistories()).isNotNull();


    }


}