package com.challenge.market.product.service;

import com.challenge.market.product.domain.Product;
import com.challenge.market.product.domain.constant.ProductStatus;
import com.challenge.market.product.dto.ProductResponse;
import com.challenge.market.product.exception.ProductRegistrationFailedException;
import com.challenge.market.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * 등록된 모든 제품 조회
 * 제품 등록 테스트
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("제품 등록 실패")
    void testProductAddFailure() throws Exception {
        // given
        Product product = new Product(10000,"제품1", ProductStatus.SALES);

        // when
        // 제품 등록시 예외를 발생시킨다.
        // thenThrow 는 값을 반환하는 메서드의 테스트에 많이 사용.
        // doThrow 는 void 메서드에 대한 테스트에 사용
        //doThrow(DataIntegrityViolationException.class).
        when(productRepository.save(any(Product.class)))
                .thenThrow(DataIntegrityViolationException.class);

        // then
        // 발생된 예외를 service 에서 잡았을 때 ProductRegisterationFailedException이 발생하는지 확인
        assertThatThrownBy(() -> productService.add(product))
                .isInstanceOf(ProductRegistrationFailedException.class);

    }

    @Test
    @DisplayName("제품 조회 테스트")
    void testProductFindSuccess() throws Exception {
        Product product = createProduct();

        // given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // when
        Product foundProduct = productService.get(1L);

        // then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(product.getId());
        assertThat(foundProduct.getName()).isEqualTo(product.getName());
        assertThat(foundProduct.getProductStatus()).isEqualTo(product.getProductStatus());

        verify(productRepository,times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("제품 전체 조회 테스트")
    void testProductFindAllSuccess() throws Exception {

        // given
        int size = 5;
        doReturn(productList(size)).when(productRepository).findAll();

        // when
        List<ProductResponse> productResponseList = productService.findAll();

        // then
        assertThat(productResponseList.size()).isEqualTo(size);
    }

    private List<Product> productList( int size) {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i <size ; i++) {
            productList.add(new Product(10000,"제품"+i,ProductStatus.SALES));
        }
        return productList;
    }


    private Product createProduct() {
        return Product.builder().
                id(1L)
                .name("손톱깎이")
                .price(10000)
                .productStatus(ProductStatus.SALES).build();
    }








}
