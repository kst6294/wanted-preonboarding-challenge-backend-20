package com.challenge.market.product.repository;

import com.challenge.market.product.domain.Product;
import com.challenge.market.product.domain.constant.ProductStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * ProductRepositoryTest
 * 제품 도메인 통합 테스트
 *
 */
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

/*
    @Test
    @DisplayName("프로덕트 레파지토리가 null이 아님")
    void productRepositoryIsNotNull() throws Exception {
        // given
        assertThat(productRepository).isNotNull();
    }
*/

    @Test
    @DisplayName("제품 등록 성공")
    void saveProductSuccess() throws Exception {

        // given
        Product product = new Product();
        // when
        Product save = productRepository.save(product);

        // then
        assertThat(save.getId()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("제품 목록 조회")
    void findAllProduct() throws Exception {
        // given
        List<Product> saveProducts = getProducts();
        // when
        List<Product> products = productRepository.findAll();
        // then
        assertThat(saveProducts.size()).isEqualTo(products.size());
    }

    private List<Product> getProducts() {
        List<Product> products= new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Product save = productRepository.save(new Product(10000, "goods" + i, ProductStatus.SALES));
            products.add(save);
        }

        return products;
    }


}
