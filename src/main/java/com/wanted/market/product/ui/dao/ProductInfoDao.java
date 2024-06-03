package com.wanted.market.product.ui.dao;

import com.wanted.market.common.http.dto.request.PageRequest;
import com.wanted.market.product.ui.dto.response.ProductInfoResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductInfoDao {
    List<ProductInfoResponse> finaALl(PageRequest pageRequest);
}
