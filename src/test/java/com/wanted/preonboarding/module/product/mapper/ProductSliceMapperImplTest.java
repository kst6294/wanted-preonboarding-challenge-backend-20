package com.wanted.preonboarding.module.product.mapper;

import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProductSliceMapperImplTest {

    @InjectMocks
    private ProductSliceMapperImpl productSliceMapper;

    @Test
    @DisplayName("toSlice: 데이터를 포함한 슬라이스 반환")
    void toSlice_withData() {
        List<Sku> data = ProductFactory.generateSkus(6);
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        ItemFilter filter = mock(ItemFilter.class);

        CustomSlice<Sku> customSlice = productSliceMapper.toSlice(data, pageable, filter);

        assertThat(customSlice).isNotNull();
        assertThat(customSlice.getContent()).hasSize(5);
        assertFalse(customSlice.isLast());
        assertThat(customSlice.getLastDomainId()).isEqualTo(data.get(5).getId());
    }

    @Test
    @DisplayName("toSlice: 빈 데이터 처리")
    void toSlice_emptyData() {
        List<Sku> data = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        ItemFilter filter = mock(ItemFilter.class);

        CustomSlice<Sku> customSlice = productSliceMapper.toSlice(data, pageable, filter);

        assertThat(customSlice).isNotNull();
        assertThat(customSlice.getContent()).isEmpty();
        assertTrue(customSlice.isLast());
        assertThat(customSlice.getLastDomainId()).isNull();
    }

}