package com.wanted.market.common.http.dto.request;

public abstract class PageRequest {
    private Integer pageNumber;
    private Integer pageSize;

    public PageRequest(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
