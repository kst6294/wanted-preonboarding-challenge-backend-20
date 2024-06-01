package com.example.wanted.product.domain;

import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.user.domain.Role;
import com.example.wanted.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    void ProductCreate로_제품을_등록할_수_있다(){
        //given
        ProductCreate productCreate = ProductCreate.builder()
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .build();

        User user = User.builder()
                .name("홍길동")
                .account("test@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        //when
        Product product = Product.from(productCreate, user);

        //then
        assertThat(product.getName()).isEqualTo("로지텍G SuperLight2");
        assertThat(product.getPrice()).isEqualTo(150000);
        assertThat(product.getQuantity()).isEqualTo(10);
        assertThat(product.getSeller().getName()).isEqualTo("홍길동");
        assertThat(product.getSeller().getAccount()).isEqualTo("test@gmail.com");
        assertThat(product.getSeller().getPassword()).isEqualTo("test1234");
        assertThat(product.getSeller().getRole()).isEqualTo(Role.USER);
    }

    @Test
    void Product의_재고를_감소시킬_수_있다(){
        //given
        ProductCreate productCreate = ProductCreate.builder()
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .build();

        User user = User.builder()
                .name("홍길동")
                .account("test@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.from(productCreate, user);

        //when
        product.deductQuantity();

        //then
        assertThat(product.getName()).isEqualTo("로지텍G SuperLight2");
        assertThat(product.getPrice()).isEqualTo(150000);
        assertThat(product.getQuantity()).isEqualTo(9);
        assertThat(product.getSellingStatus()).isEqualTo(ProductSellingStatus.SELLING);
        assertThat(product.getSeller().getName()).isEqualTo("홍길동");
        assertThat(product.getSeller().getAccount()).isEqualTo("test@gmail.com");
        assertThat(product.getSeller().getPassword()).isEqualTo("test1234");
        assertThat(product.getSeller().getRole()).isEqualTo(Role.USER);
    }

    @Test
    void Product의_재고가_1개_남았었으면_판매_상태를_예약중으로_변경한다(){
        //given
        ProductCreate productCreate = ProductCreate.builder()
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(1)
                .build();

        User user = User.builder()
                .name("홍길동")
                .account("test@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.from(productCreate, user);

        //when
        product.deductQuantity();

        //then
        assertThat(product.getName()).isEqualTo("로지텍G SuperLight2");
        assertThat(product.getPrice()).isEqualTo(150000);
        assertThat(product.getQuantity()).isEqualTo(0);
        assertThat(product.getSellingStatus()).isEqualTo(ProductSellingStatus.RESERVATION);
        assertThat(product.getSeller().getName()).isEqualTo("홍길동");
        assertThat(product.getSeller().getAccount()).isEqualTo("test@gmail.com");
        assertThat(product.getSeller().getPassword()).isEqualTo("test1234");
        assertThat(product.getSeller().getRole()).isEqualTo(Role.USER);
    }

    @Test
    void 재고가_없으면_재고를_감소시킬_수_없다(){
        //given
        ProductCreate productCreate = ProductCreate.builder()
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(1)
                .build();

        User user = User.builder()
                .name("홍길동")
                .account("test@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.from(productCreate, user);
        product.deductQuantity();

        //when
        //then
        assertThatThrownBy(() ->
                product.deductQuantity()
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void user가_판매자이면_true를_반환한다() {
        //given
        ProductCreate productCreate = ProductCreate.builder()
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(1)
                .build();

        User user = User.builder()
                .id(1L)
                .name("홍길동")
                .account("test@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.from(productCreate, user);

        //when
        boolean result = product.checkSeller(user);

        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void user가_판매자가_아니면_true를_반환한다() {
        //given
        ProductCreate productCreate = ProductCreate.builder()
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(1)
                .build();

        User user = User.builder()
                .id(1L)
                .name("홍길동")
                .account("test@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("임꺽정")
                .account("test12@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.from(productCreate, user);

        //when
        boolean result = product.checkSeller(user2);

        //then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void 판매_완료_상태로_변경할_수_있다() {
        //given
        ProductCreate productCreate = ProductCreate.builder()
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(1)
                .build();

        User user = User.builder()
                .id(1L)
                .name("홍길동")
                .account("test@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.builder()
                .id(1L)
                .seller(user)
                .price(1000)
                .quantity(0)
                .sellingStatus(ProductSellingStatus.RESERVATION)
                .name("아이스크림")
                .build();

        //when
        product.complete();

        //then
        assertThat(product.getSellingStatus()).isEqualTo(ProductSellingStatus.COMPLETE);
    }

    @Test
    void 재고가_남아있으면_판매_완료할_수_없다() {
        //given
        ProductCreate productCreate = ProductCreate.builder()
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(1)
                .build();

        User user = User.builder()
                .id(1L)
                .name("홍길동")
                .account("test@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.builder()
                .id(1L)
                .seller(user)
                .price(1000)
                .quantity(1)
                .sellingStatus(ProductSellingStatus.COMPLETE)
                .name("아이스크림")
                .build();

        //when
        //then
        assertThatThrownBy(() ->
                product.complete()
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 예약중이_아니면_판매_완료할_수_없다() {
        //given
        ProductCreate productCreate = ProductCreate.builder()
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(1)
                .build();

        User user = User.builder()
                .id(1L)
                .name("홍길동")
                .account("test@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();

        Product product = Product.builder()
                .id(1L)
                .seller(user)
                .price(1000)
                .quantity(0)
                .sellingStatus(ProductSellingStatus.COMPLETE)
                .name("아이스크림")
                .build();

        //when
        //then
        assertThatThrownBy(() ->
                product.complete()
        ).isInstanceOf(IllegalStateException.class);
    }

}