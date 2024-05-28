package com.wanted.preonboarding.module.product.mapper;

import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductSliceMapper {

    CustomSlice<Sku> toSlice(List<Sku> data, Pageable pageable, ItemFilter filter);

}
