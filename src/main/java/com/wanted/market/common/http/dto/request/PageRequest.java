package com.wanted.market.common.http.dto.request;

public class PageRequest {
    private final Integer pageNumber;
    private final Integer pageSize;

    public PageRequest(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getOffset() {
        return (pageNumber - 1) * pageSize;
    }
}
