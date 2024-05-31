package com.example.wanted.order.domain;

import com.example.wanted.product.domain.Product;
import com.example.wanted.product.domain.ProductSellingStatus;
import com.example.wanted.user.domain.Role;
import com.example.wanted.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void OrderCreate로_회원은_상품을_주문할_수_있다(){
        //given
        User buyer = User.builder()
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        User seller = User.builder()
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.builder()
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();

        //when
        Order order = Order.from(buyer, product);

        //then
        assertThat(order.getPrice()).isEqualTo(150000);
        assertThat(order.getProduct().getName()).isEqualTo("로지텍G SuperLight2");
        assertThat(order.getProduct().getQuantity()).isEqualTo(10);
        assertThat(order.getSeller().getName()).isEqualTo("엄꺽정");
        assertThat(order.getSeller().getAccount()).isEqualTo("seller@gmail.com");
        assertThat(order.getBuyer().getAccount()).isEqualTo("buy@gmail.com");
        assertThat(order.getBuyer().getName()).isEqualTo("홍길동");
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.REQUEST);
    }

    @DisplayName("")
    @Test
    void test(){
        //given

        //when

        //then
    }

}