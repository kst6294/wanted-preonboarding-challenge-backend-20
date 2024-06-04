package com.wanted.market.product.ui.dao;

import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.common.http.dto.request.PageRequest;
import com.wanted.market.product.ui.dto.response.ProductInfoResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductInfoDao {
    List<ProductInfoResponse> findAll(PageRequest pageRequest);

    Long count(PageRequest pageRequest);

    ProductInfoResponse findById(Long id);

    default ProductInfoResponse findByIdOrThrow(Long id) throws NotFoundException {
        ProductInfoResponse findProductInfo = findById(id);
        if (findProductInfo == null)
            throw new NotFoundException("no such product");
        return findProductInfo;
    }
}
