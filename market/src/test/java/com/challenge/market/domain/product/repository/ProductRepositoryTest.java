package com.challenge.market.domain.product.repository;

import com.challenge.market.domain.product.entity.Product;
import com.challenge.market.domain.product.constant.ProductStatus;
import com.challenge.market.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        // 등록된 제품은 제품명, 가격, 예약상태가 포함

        Product product = new Product(10000,"제품1",ProductStatus.SALES);

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct.getId()).isEqualTo(product.getId());
        assertThat(savedProduct.getName()).isEqualTo(product.getName());
        assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());

    }

    @Test
    @DisplayName("제품 목록 조회")
    void findAllProducts() throws Exception {
        // given
        List<Product> saveProducts = productList();
        // when
        List<Product> products = productRepository.findAll();
        // then
        assertThat(saveProducts.size()).isEqualTo(products.size());
    }

    private List<Product> productList() {
        List<Product> products= new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Product save = productRepository.save(new Product(10000, "goods" + i, ProductStatus.SALES));
            products.add(save);
        }
        return products;
    }

    @Test
    @DisplayName("제품 상세페이지 조회 성공")
    void findProduct() throws Exception {
        // given
        Product product = new Product(10000, "토끼슬리퍼", ProductStatus.SALES);

        Product savedProduct = productRepository.save(product);

        // when
        Optional<Product> foundProduct = productRepository.findById(product.getId());

        // then
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getId()).isEqualTo(savedProduct.getId());
        assertThat(foundProduct.get().getName()).isEqualTo(savedProduct.getName());
        assertThat(foundProduct.get().getPrice()).isEqualTo(savedProduct.getPrice());


    }


}
