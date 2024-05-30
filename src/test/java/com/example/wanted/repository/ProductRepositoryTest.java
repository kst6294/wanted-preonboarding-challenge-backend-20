package com.example.wanted.repository;

import com.example.wanted.model.Product;
import com.example.wanted.model.State;
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
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("ProductRepository Test")
    void InsertProduct() {
        Product product = Product.builder().name("테스트 물건").description("테스트").price(10000).seller("테스터").state(State.ONSALE).build();

        Product result = productRepository.save(product);

        Assertions.assertThat(result.getName()).isEqualTo(product.getName());
        Assertions.assertThat(result.getDescription()).isEqualTo(product.getDescription());
        Assertions.assertThat(result.getPrice()).isEqualTo(product.getPrice());
        Assertions.assertThat(result.getSeller()).isEqualTo(product.getSeller());
        Assertions.assertThat(result.getState()).isEqualTo(product.getState());
    }
}