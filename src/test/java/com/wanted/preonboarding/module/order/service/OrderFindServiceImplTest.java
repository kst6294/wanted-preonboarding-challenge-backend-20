package com.wanted.preonboarding.module.order.service;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.repository.OrderFindRepository;
import com.wanted.preonboarding.module.order.repository.OrderFindRepositoryImpl;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderFindServiceImplTest {

    @InjectMocks
    private OrderFindServiceImpl orderFindService;

    @Mock
    private OrderFindRepositoryImpl orderFindRepository;


    @Test
    @DisplayName("주문 단일 조회 - 성공")
    void fetchOrderEntity_success() {
        // given
        Users buyer = UsersModuleHelper.toUsers();

        Users seller = UsersModuleHelper.toUsers();

        Product product = ProductModuleHelper.toProduct();
        product.setSeller(seller);
        Order order = OrderModuleHelper.toOrderWithId(product, buyer);


        when(orderFindRepository.fetchOrderEntity(order.getId())).thenReturn(Optional.of(order));

        Order findOrder = orderFindService.fetchOrderEntity(order.getId());

        // then
        assertThat(findOrder).isNotNull();
        assertThat(findOrder.getBuyer()).isNotNull();
        assertThat(findOrder.getSeller()).isNotNull();
    }

}