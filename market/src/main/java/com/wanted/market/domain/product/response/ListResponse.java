package com.wanted.market.domain.product.response;

import com.wanted.market.domain.product.dto.ProductDto;
import com.wanted.market.global.common.code.ResponseCode;
import com.wanted.market.global.common.response.BaseResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ListResponse extends BaseResponse {

    List<ProductDto> productList = new ArrayList<>();

    public ListResponse(ResponseCode response, List<ProductDto> productList) {
        super(response);
        this.productList = productList;
    }

    public ListResponse(ResponseCode response) {
        super(response);
    }
}
