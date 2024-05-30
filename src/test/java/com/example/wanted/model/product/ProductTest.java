package com.example.wanted.model.product;


import com.example.wanted.model.Product;
import com.example.wanted.model.State;
import com.example.wanted.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ProductTest {
    @Test
    @DisplayName("물품 등록 테스트")
    void InsertProductTest() {
        User user = User.builder().u_id(1L).name("테스터").build();

        Product product = Product.builder().user(user).name("테스트 물건").description("테스트").price(10000).seller(user.getName()).state(State.ONSALE).build();
        Assertions.assertThat(product.getUser()).isEqualTo(user);
        Assertions.assertThat(product.getName()).isEqualTo("테스트 물건");
        Assertions.assertThat(product.getDescription()).isEqualTo("테스트");
        Assertions.assertThat(product.getPrice()).isEqualTo(10000);
        Assertions.assertThat(product.getSeller()).isEqualTo("테스터");
        Assertions.assertThat(product.getState()).isEqualTo(State.ONSALE);

    }
}