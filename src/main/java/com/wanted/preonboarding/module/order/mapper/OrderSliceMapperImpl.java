package com.wanted.preonboarding.module.order.mapper;

import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import com.wanted.preonboarding.module.common.mapper.AbstractProductSliceMapper;
import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderSliceMapperImpl extends AbstractProductSliceMapper<DetailedOrderContext>  implements OrderSliceMapper{


    @Override
    public CustomSlice<DetailedOrderContext> toSlice(List<DetailedOrderContext> data, Pageable pageable, ItemFilter filter) {
        return super.toSlice(data, pageable, filter);
    }
}
