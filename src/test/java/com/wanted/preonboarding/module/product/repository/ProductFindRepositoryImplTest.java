package com.wanted.preonboarding.module.product.repository;

import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.BaseFetchRepositoryTest;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.filter.ProductFilter;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductFindRepositoryImplTest extends BaseFetchRepositoryTest {

    private Product product;
    private List<Product> products;

    @Autowired
    ProductFindRepositoryImpl productFindRepository;

    @Test
    @DisplayName("BaseSku 조회")
    void fetchBaseSku() {
        // Product 저장
        saveProduct();

        assertThat(product.getId()).isNotNull();

        // Product 조회
        Optional<BaseSku> baseSku = productFindRepository.fetchBaseSku(product.getId());
        assertThat(baseSku).isPresent();
        assertThat(baseSku.get().getProductName()).isEqualTo(product.getProductName());
        assertThat(baseSku.get().getPrice()).isEqualTo(product.getPrice());
        assertThat(baseSku.get().getSeller()).isEqualTo(product.getSeller().getEmail());
    }


    @Test
    @DisplayName("BaseSku 리스트 조회 - 아무 조건 없을때")
    void fetchBaseSkus() {
        // Product 저장
        saveProducts(10);
        Pageable pageable = PageRequest.of(0, 5);

        ProductFilter filter = new ProductFilter(null, null);
        List<BaseSku> baseSkus = productFindRepository.fetchBaseSkus(filter, pageable);

        assertEquals(6, baseSkus.size());
        assertThat(baseSkus.get(0).getProductName()).isNotBlank();
        assertThat(baseSkus.get(0).getSeller()).isNotBlank();

    }

    @Test
    @DisplayName("BaseSku 리스트 조회 - 노 오프셋 조회")
    void fetchBaseSkus_with_last_domain_id() {
        // Product 저장
        saveProducts(10);
        Pageable pageable = PageRequest.of(0, 10);

        Product middleProduct = products.get(4);

        ProductFilter filter = new ProductFilter(middleProduct.getId(), null);
        List<BaseSku> baseSkus = productFindRepository.fetchBaseSkus(filter, pageable);

        assertEquals(5, baseSkus.size());
        assertThat(baseSkus.get(0).getProductName()).isNotBlank();
        assertThat(baseSkus.get(0).getSeller()).isNotBlank();
    }




    @Test
    @DisplayName("Product Entity 조회 - Seller fetch Join")
    void fetchProduct_with_fetch_seller() {
        // Product 저장
        saveProduct();

        Optional<Product> productOpt = productFindRepository.fetchProductEntity(product.getId());

        assertThat(productOpt).isPresent();
        assertThat(productOpt.get().getProductName()).isNotBlank();
        assertThat(productOpt.get().getSeller()).isNotNull();
        assertThat(productOpt.get().getSeller().getEmail()).isEqualTo(product.getSeller().getEmail());
    }








    private void saveProduct() {
        Users users = UsersModuleHelper.toUsers();
        getEntityManager().persist(users);
        Product newProduct = ProductModuleHelper.toProduct();
        newProduct.setSeller(users);
        getEntityManager().persist(newProduct);

        product = newProduct;
    }


    private void saveProducts(int count) {
        Users users = UsersModuleHelper.toUsers();
        getEntityManager().persist(users);

        List<Product> datas = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Product newProduct = ProductModuleHelper.toProduct();
            newProduct.setSeller(users);
            getEntityManager().persist(newProduct);
            datas.add(newProduct);
        }
        products = datas;
        flushAndClear();
    }


}