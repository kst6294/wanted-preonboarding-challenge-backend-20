package com.wanted.preonboarding.module.common.mapper;

import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.common.filter.LastDomainIdFilter;
import com.wanted.preonboarding.module.utils.SliceUtils;
import com.wanted.preonboarding.module.utils.SortUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractProductSliceMapper <T extends CursorValueProvider> implements ProductSliceMapper<T> {

    @Override
    public CustomSlice<T> toSlice(List<T> data, Pageable pageable,  LastDomainIdFilter filter) {
        if (data.isEmpty()) {
            return buildCustomSlice(SliceUtils.emptySlice(pageable), null, null);
        }

        Long lastDomainId = null;
        String cursorValue = null;
        Slice<T> slice = SliceUtils.toSlice(data, pageable);

        T lastItem = data.getLast();
        lastDomainId = lastItem.getId();
        cursorValue = SortUtils.setCursorValue(lastItem, filter.getOrderType());

        Comparator<CursorValueProvider> comparator = SortUtils.getComparatorBasedOnOrderType(filter.getOrderType());
        if (comparator != null) {
            List<T> sortedData = data.stream().sorted(comparator).collect(Collectors.toList());
            lastItem = sortedData.getLast();
            lastDomainId = lastItem.getId();
            cursorValue = SortUtils.setCursorValue(lastItem, filter.getOrderType());
            slice = SliceUtils.toSlice(sortedData, pageable);
        }

        return buildCustomSlice(slice, lastDomainId, cursorValue);
    }

    private CustomSlice<T> buildCustomSlice(Slice<T> slice, Long lastDomainId, String cursorValue){
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
                .cursorValue(cursorValue)
                .build();
    }

}
