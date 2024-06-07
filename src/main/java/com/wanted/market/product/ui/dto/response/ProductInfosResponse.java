package com.wanted.market.product.ui.dto.response;

import com.wanted.market.common.http.dto.response.PageInfo;
import com.wanted.market.common.http.dto.response.PageResponse;

import java.util.List;

public class ProductInfosResponse<T extends SimpleProductInfoResponse> extends PageResponse<T> {
    public ProductInfosResponse(List<T> content, PageInfo pageInfo) {
        super(content, pageInfo);
    }
}
