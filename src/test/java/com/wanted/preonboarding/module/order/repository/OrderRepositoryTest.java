package com.wanted.preonboarding.module.order.repository;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.BaseFetchRepositoryTest;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderRepositoryTest extends BaseFetchRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    private Users seller;
    private Users buyer;
    private Product product;
    private Order order;

    @BeforeEach
    void setUp() {
        seller = UsersModuleHelper.toUsers();
        getEntityManager().persist(seller);
        product = ProductModuleHelper.toProduct();
        product.setSeller(seller);
        getEntityManager().persist(product);
        buyer = UsersModuleHelper.toUsers();
        getEntityManager().persist(buyer);

        order = OrderModuleHelper.toOrder(product, buyer);

    }


    @Test
    @DisplayName("Order 저장 및 조회")
    void saveAndFindOrder() {
        // Product 저장
        Order savedOrder = orderRepository.save(order);
        assertThat(savedOrder.getId()).isNotNull();

        // Product 조회
        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getId());
        assertThat(foundOrder).isPresent();

        assertThat(foundOrder.get().getOrderStatus()).isEqualTo(savedOrder.getOrderStatus());
        assertThat(foundOrder.get().getProduct()).isEqualTo(product);
        assertThat(foundOrder.get().getSeller()).isEqualTo(seller);
        assertThat(foundOrder.get().getBuyer()).isEqualTo(buyer);

    }

}