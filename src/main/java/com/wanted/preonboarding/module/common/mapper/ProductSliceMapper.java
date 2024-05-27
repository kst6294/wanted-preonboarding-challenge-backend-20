package com.wanted.preonboarding.module.common.mapper;

import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.common.filter.LastDomainIdFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductSliceMapper<T extends CursorValueProvider>  {

    CustomSlice<T> toSlice(List<T> data, Pageable pageable, LastDomainIdFilter filter);

}
