package com.example.wanted.order.domain;

import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.product.domain.Product;
import com.example.wanted.product.domain.ProductSellingStatus;
import com.example.wanted.user.domain.Role;
import com.example.wanted.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void OrderCreate로_회원은_상품을_주문할_수_있다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        User seller = User.builder()
                .id(2L)
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
        assertThat(order.getStatus()).isEqualTo(OrderStatus.REQUEST);
    }

    @Test
    void 판매자는_자신의_제품에_구매요청을_생성할_수_없습니다(){
        //given

        User seller = User.builder()
                .id(1L)
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
        //then
        assertThatThrownBy(() -> Order.from(seller, product)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문을_승인할_수_있다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        Order order = Order.from(buyer, product);

        //when
        order.approve(seller);

        //then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.APPROVAL);
    }

    @Test
    void 주문승인_시_주문_상태가_구매_요청이_아니면_예외가_발생한다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        Order order = Order.from(buyer, product);
        order.approve(seller);

        //when
        //then
        assertThatThrownBy(() -> order.approve(seller)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 판매자가_아니면_주문을_승인할_수_없다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        Order order = Order.from(buyer, product);

        //when
        //then
        assertThatThrownBy(() -> order.approve(buyer)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 판매자이면_true를_반환한다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        Order order = Order.from(buyer, product);

        //when
        boolean result = order.checkSeller(seller);


        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void 판매자가_아니면_false를_반환한다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        Order order = Order.from(buyer, product);

        //when
        boolean result = order.checkSeller(buyer);


        //then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void 구매자이면_true를_반환한다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        Order order = Order.from(buyer, product);

        //when
        boolean result = order.checkBuyer(buyer);


        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void 구매자가_아니면_false를_반환한다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        Order order = Order.from(buyer, product);

        //when
        boolean result = order.checkBuyer(seller);


        //then
        assertThat(result).isEqualTo(false);
    }
}