package com.wanted.market.common.http.dto.response;

import java.util.List;

public class PageResponse<T> {
    private final List<? extends T> content;
    private final PageInfo pageInfo;

    public PageResponse(List<? extends T> content, PageInfo pageInfo) {
        this.content = content;
        this.pageInfo = pageInfo;
    }

    public List<? extends T> getContent() {
        return content;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }
}
