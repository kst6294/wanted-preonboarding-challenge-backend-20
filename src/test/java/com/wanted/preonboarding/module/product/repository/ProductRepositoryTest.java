package com.wanted.preonboarding.module.product.repository;

import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.BaseFetchRepositoryTest;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductRepositoryTest extends BaseFetchRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    private Users users;
    private Product product;

    @BeforeEach
    void setUp() {
        users = UsersModuleHelper.toUsers();
        getEntityManager().persist(users);
        product = ProductModuleHelper.toProduct();
        product.setSeller(users);
    }

    @Test
    @DisplayName("Product 저장 및 조회")
    void saveAndFindProduct() {
        // Product 저장
        Product savedProduct = productRepository.save(product);
        assertThat(savedProduct.getId()).isNotNull();

        // Product 조회
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getProductName()).isEqualTo(product.getProductName());
        assertThat(foundProduct.get().getPrice()).isEqualTo(product.getPrice());
        assertThat(foundProduct.get().getSeller().getEmail()).isEqualTo(users.getEmail());
    }

    @Test
    @DisplayName("Product 삭제")
    void deleteProduct() {
        // Product 저장
        Product savedProduct = productRepository.save(product);
        assertThat(savedProduct.getId()).isNotNull();

        // Product 삭제
        productRepository.delete(savedProduct);

        // 삭제 확인
        Optional<Product> deletedProduct = productRepository.findById(savedProduct.getId());
        assertThat(deletedProduct).isNotPresent();
    }




}