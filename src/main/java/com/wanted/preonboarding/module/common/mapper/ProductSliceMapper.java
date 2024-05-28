package com.wanted.preonboarding.module.common.mapper;

import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.common.filter.LastDomainIdFilter;
import com.wanted.preonboarding.module.common.filter.SortFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductSliceMapper<T extends LastDomainIdProvider>  {

    CustomSlice<T> toSlice(List<T> data, Pageable pageable, SortFilter filter);

}
