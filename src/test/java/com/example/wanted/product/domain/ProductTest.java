package com.example.wanted.product.domain;

import com.example.wanted.user.domain.Role;
import com.example.wanted.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

}