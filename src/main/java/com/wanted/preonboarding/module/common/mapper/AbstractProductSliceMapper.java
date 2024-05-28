package com.wanted.preonboarding.module.common.mapper;

import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.common.filter.SortFilter;
import com.wanted.preonboarding.module.utils.SliceUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractProductSliceMapper <T extends LastDomainIdProvider> implements ProductSliceMapper<T> {

    @Override
    public CustomSlice<T> toSlice(List<T> data, Pageable pageable,  SortFilter filter) {
        if (data.isEmpty()) {
            return buildCustomSlice(SliceUtils.emptySlice(pageable), null);
        }

        Long lastDomainId = null;
        Slice<T> slice = SliceUtils.toSlice(data, pageable);

        T lastItem = data.getLast();
        lastDomainId = lastItem.getId();

        return buildCustomSlice(slice, lastDomainId);
    }

    private CustomSlice<T> buildCustomSlice(Slice<T> slice, Long lastDomainId){
        return CustomSlice.<T>builder()
                .content(slice.getContent())
                .last(slice.isLast())
                .first(slice.isFirst())
                .number(slice.getNumber())
                .sort(slice.getSort())
                .size(slice.getSize())
                .numberOfElements(slice.getNumberOfElements())
                .empty(slice.isEmpty())
                .lastDomainId(lastDomainId)
                .build();
    }

}
