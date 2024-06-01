package com.example.wanted.model.transaction;

import com.example.wanted.model.Product;
import com.example.wanted.model.State;
import com.example.wanted.model.Transaction;
import com.example.wanted.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class TransactionTest {

    @Test
    @DisplayName("거래 생성 테스트")
    void orderInsertTest() {
        User buyer = User.builder().u_id(1L).email("test1@naver.com").password("11111111").name("테스터").role("ROLE_USER").build();
        User seller = User.builder().u_id(2L).email("test2@naver.com").password("11111111").name("테스터2").role("ROLE_USER").build();
        Product product = Product.builder().user(seller).name("테스트 물건").description("테스트").price(10000).seller(seller.getName()).state(State.ONSALE).build();
        Transaction transaction = Transaction.builder()
                .t_id(1L).user(buyer).product(product).build();

        Assertions.assertThat(transaction.getT_id()).isEqualTo(1L);
        Assertions.assertThat(transaction.getUser()).isEqualTo(buyer);
        Assertions.assertThat(transaction.getProduct()).isEqualTo(product);

    }
}