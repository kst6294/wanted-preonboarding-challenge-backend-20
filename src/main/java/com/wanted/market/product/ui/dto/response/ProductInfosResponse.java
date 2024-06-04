package com.wanted.market.product.ui.dto.response;

import com.wanted.market.common.http.dto.response.PageInfo;

import java.util.List;

public record ProductInfosResponse(
        List<ProductInfoResponse> content,
        PageInfo pageInfo
) {
}
