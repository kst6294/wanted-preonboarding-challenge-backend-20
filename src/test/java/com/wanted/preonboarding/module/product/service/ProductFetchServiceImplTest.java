package com.wanted.preonboarding.module.product.service;

import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.exception.product.NotFoundProductException;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.filter.ItemFilter;
import com.wanted.preonboarding.module.product.mapper.ProductSliceMapperImpl;
import com.wanted.preonboarding.module.product.repository.ProductFindRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductFetchServiceImplTest {

    @Mock
    private ProductFindRepository productFindRepository;

    @Spy
    private ProductSliceMapperImpl productSliceMapper;

    @InjectMocks
    private ProductFetchServiceImpl productFetchService;


    @Test
    @DisplayName("상품 단일 조회 - 성공")
    void fetchProduct_success() {
        // given
        BaseSku baseSku = ProductFactory.generateBaseSku();


        when(productFindRepository.fetchBaseSku(baseSku.getId())).thenReturn(Optional.of(baseSku));

        // when
        Sku result = productFetchService.fetchProduct(baseSku.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(baseSku.getId());
        assertThat(result.getProductName()).isEqualTo(baseSku.getProductName());
        assertThat(result.getPrice()).isEqualTo(baseSku.getPrice());
        assertThat(result.getSeller()).isEqualTo(baseSku.getSeller());
    }

    @Test
    @DisplayName("상품 단일 조회 - 실패")
    void fetchProduct_notFound() {
        // given
        long productId = 1L;

        when(productFindRepository.fetchBaseSku(productId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundProductException.class, () -> productFetchService.fetchProduct(productId));
    }

    @Test
    @DisplayName("상품 목록 조회")
    void fetchProducts() {
        // given
        ItemFilter filter = mock(ItemFilter.class);
        Pageable pageable = PageRequest.of(0, 5);
        List<BaseSku> baseSkus = ProductFactory.generateBaseSkus(6);

        when(productFindRepository.fetchBaseSkus(filter, pageable)).thenReturn(baseSkus);

        CustomSlice<Sku> customSlice = productSliceMapper.toSlice(new ArrayList<>(baseSkus), pageable, filter);

        doReturn(customSlice).when(productSliceMapper).toSlice(anyList(), any(Pageable.class), any(ItemFilter.class));

        // when
        CustomSlice<Sku> result = productFetchService.fetchProducts(filter, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(5);

        assertThat(result.getContent().get(0).getProductName()).isEqualTo(baseSkus.get(0).getProductName());
    }


}