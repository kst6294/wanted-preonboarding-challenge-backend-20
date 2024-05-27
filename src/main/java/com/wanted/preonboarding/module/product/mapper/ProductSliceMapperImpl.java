package com.wanted.preonboarding.module.product.mapper;

import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.common.mapper.AbstractProductSliceMapper;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.filter.ItemFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductSliceMapperImpl extends AbstractProductSliceMapper<Sku> implements ProductSliceMapper {

    @Override
    public CustomSlice<Sku> toSlice(List<Sku> data, Pageable pageable, ItemFilter filter) {
        return super.toSlice(data, pageable, filter);
    }

}
