package com.wanted.preonboarding.module.order.mapper;

import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderSliceMapper {

    CustomSlice<DetailedOrderContext> toSlice(List<DetailedOrderContext> data, Pageable pageable, ItemFilter filter);

}
