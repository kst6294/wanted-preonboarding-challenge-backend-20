package com.wanted.market.product.ui.dto.request;

public class QueryMineRequest extends QueryRequest {
    private Boolean hasOrder;

    public QueryMineRequest(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }

    public Boolean getHasOrder() {
        return hasOrder;
    }

    public void setHasOrder(Boolean hasOrder) {
        this.hasOrder = hasOrder;
    }
}
