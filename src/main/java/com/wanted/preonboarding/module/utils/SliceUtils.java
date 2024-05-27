package com.wanted.preonboarding.module.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.Collections;
import java.util.List;

public class SliceUtils {

    public static <T> Slice<T> toSlice(List<T> contents, Pageable pageable) {

        boolean hasNext = isContentSizeGreaterThanPageSize(contents, pageable);
        return new SliceImpl<>(hasNext ? subListLastContent(contents, pageable) : contents, pageable, hasNext);
    }

    public static <T> Slice<T> emptySlice(Pageable pageable) {
        List<T> emptyList = Collections.emptyList();
        return new SliceImpl<>(emptyList, pageable, false);
    }



    private static <T> boolean isContentSizeGreaterThanPageSize(List<T> content, Pageable pageable) {
        return pageable.isPaged() && content.size() == pageable.getPageSize() + 1;
    }

    private static <T> List<T> subListLastContent(List<T> content, Pageable pageable) {
        return content.subList(0, pageable.getPageSize());
    }


}
