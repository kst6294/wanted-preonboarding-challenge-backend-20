package com.wanted.market.common.http.dto.response;

public record PageInfo(
        Integer pageNumber,
        Integer pageSize,
        Integer totalPages,
        Long totalElements,
        Boolean isFirst,
        Boolean isLast
) {
}
