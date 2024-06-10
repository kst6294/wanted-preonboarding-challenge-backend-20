package com.wanted.market.global.util;

import com.wanted.market.common.http.dto.request.PageRequest;
import com.wanted.market.common.http.dto.response.PageInfo;

import java.util.function.Function;

public final class PageUtils {
    public static <T extends PageRequest> PageInfo getPageInfo(Function<T, Long> countQuery, T request) {
        Integer pageNumber = request.getPageNumber();
        Integer pageSize = request.getPageSize();
        Long totalElements = countQuery.apply(request);
        Integer totalPages = (int) Math.ceil((double) totalElements / (double) pageSize);
        boolean isFirst = pageNumber <= 1;
        boolean isLast = pageNumber >= totalPages;
        return new PageInfo(pageNumber, pageSize, totalPages, totalElements, isFirst, isLast);
    }
}
