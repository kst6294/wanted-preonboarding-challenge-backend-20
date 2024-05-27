package com.wanted.preonboarding.module.order.repository;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.BaseFetchRepositoryTest;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderFindRepositoryImplTest extends BaseFetchRepositoryTest {

    @Autowired
    OrderFindRepositoryImpl orderFindRepository;

    private Order order;

    @Test
    @DisplayName("Order Entity 조회")
    void fetchOrderEntity() {
        // Product 저장
        saveOrder();

        assertThat(order.getId()).isNotNull();

        // Product 조회
        Optional<Order> findOrder = orderFindRepository.fetchOrderEntity(order.getId());
        assertThat(findOrder).isPresent();
        assertThat(findOrder.get().getSeller()).isNotNull();
        assertThat(findOrder.get().getBuyer()).isNotNull();

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

}