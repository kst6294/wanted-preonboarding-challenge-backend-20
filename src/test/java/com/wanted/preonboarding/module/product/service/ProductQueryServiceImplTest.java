package com.wanted.preonboarding.module.product.service;

import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.document.utils.SecuritySupportTest;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import com.wanted.preonboarding.module.product.mapper.ProductMapper;
import com.wanted.preonboarding.module.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceImplTest extends SecuritySupportTest {

    @InjectMocks
    private ProductQueryServiceImpl productQueryService;


    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductFindServiceImpl productFindService;


    @BeforeEach
    void setUp() {
        securityUserMockSetting();
    }


    @Test
    @DisplayName("Product 생성")
    void createProduct() {

        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProduct);
        BaseSku sku = ProductModuleHelper.toSku(product);

        when(productMapper.toProduct(any(CreateProduct.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toSku(any(Product.class))).thenReturn(sku);

        Sku result = productQueryService.createProduct(createProduct);
        assertThat(result).isNotNull();
        assertThat(result.getProductName()).isEqualTo(createProduct.getProductName());
        assertThat(result.getSeller()).isEqualTo(product.getSeller().getEmail());
    }

    @Test
    @DisplayName("Product 예약")
    void productBooking() {

        CreateProduct createProductWithUsers = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProductWithUsers);
        product.doBooking();

        when(productFindService.fetchProductEntity(anyLong())).thenReturn(product);

        Product bookedProduct = productQueryService.doBooking(anyLong());
        assertThat(bookedProduct).isNotNull();
        assertThat(bookedProduct.getProductStatus()).isEqualTo(ProductStatus.BOOKING);
    }

}