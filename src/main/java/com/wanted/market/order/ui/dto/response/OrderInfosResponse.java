package com.wanted.market.order.ui.dto.response;

import com.wanted.market.common.http.dto.response.PageInfo;
import com.wanted.market.common.http.dto.response.PageResponse;

import java.util.List;

public class OrderInfosResponse<T extends SimpleOrderInfoResponse> extends PageResponse<T> {
    public OrderInfosResponse(List<? extends T> content, PageInfo pageInfo) {
        super(content, pageInfo);
    }
}
