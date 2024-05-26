package com.wanted.preonboarding.module.product.mapper;

import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductMapperImplTest {

    @InjectMocks
    private ProductMapperImpl productMapper;

    private Users mockUser;
    private CreateProduct createProduct;
    private Product product;


    @BeforeEach
    void setUp() {
        createProduct = ProductModuleHelper.toCreateProductWithUsers();
        mockUser = UsersModuleHelper.toUsers();
        product = ProductModuleHelper.toProduct();
    }




    @Test
    @DisplayName("CreateProduct와 Users를 Product로 매핑")
    void toProduct() {
        Product mappedProduct = productMapper.toProduct(createProduct);

        assertThat(mappedProduct.getProductName()).isEqualTo(createProduct.getProductName());
        assertThat(mappedProduct.getPrice()).isEqualTo(createProduct.getPrice());
        assertThat(mappedProduct.getProductStatus()).isEqualTo(ProductStatus.ON_STOCK);
        assertThat(mappedProduct.getSeller().getEmail()).isEqualTo(createProduct.getUsers().getEmail());
    }

    @Test
    @DisplayName("Product를 BaseSku로 매핑")
    void toSku() {
        BaseSku baseSku = productMapper.toSku(product);

        assertThat(baseSku.getId()).isEqualTo(product.getId());
        assertThat(baseSku.getProductName()).isEqualTo(product.getProductName());
        assertThat(baseSku.getProductStatus()).isEqualTo(product.getProductStatus());
        assertThat(baseSku.getSeller()).isEqualTo(product.getSeller().getEmail());
    }

}