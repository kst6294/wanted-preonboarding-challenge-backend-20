package com.wanted.market.product.ui.dto.request;

import com.wanted.market.common.http.dto.request.PageRequest;

public class QueryRequest extends PageRequest {
    private Long sellerId;
    private String status; // preparing/on_sale/reserved/sold

    public QueryRequest(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }

    public Long getSellerId() {
        return sellerId;
    }

    public String getStatus() {
        return status;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
