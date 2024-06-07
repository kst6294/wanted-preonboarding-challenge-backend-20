package com.wanted.market.order.ui.dto.request;

import com.wanted.market.common.http.dto.request.PageRequest;

public class QueryRequest extends PageRequest {
    private Long buyerId;
    private String status; // requested, confirmed, finished

    public QueryRequest(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public String getStatus() {
        return status;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}