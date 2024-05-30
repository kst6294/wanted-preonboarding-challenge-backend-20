package com.example.wanted.repository;

import com.example.wanted.model.Product;
import com.example.wanted.model.State;
import com.example.wanted.model.Transaction;
import com.example.wanted.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("OrderRepository Test")
    void InsertTransaction() {
        User buyer = User.builder().email("test1@naver.com").password("11111111").name("테스터").role("ROLE_USER").build();
        User seller = User.builder().email("test2@naver.com").password("11111111").name("테스터2").role("ROLE_USER").build();
        Product product = Product.builder().user(seller).name("테스트용품").description("테스트").price(10000).seller(seller.getName()).state(State.ONSALE).build();

        Transaction transaction = Transaction.builder()
                .seller_id(product.getUser().getU_id()).user(buyer).product(product).build();

        Transaction transaction_result = orderRepository.save(transaction);

        Assertions.assertThat(transaction_result.getT_id()).isEqualTo(transaction.getT_id());
        Assertions.assertThat(transaction_result.getSeller_id()).isEqualTo(transaction.getSeller_id());
        Assertions.assertThat(transaction_result.getUser()).isEqualTo(transaction.getUser());
        Assertions.assertThat(transaction_result.getProduct()).isEqualTo(transaction.getProduct());
    }
}