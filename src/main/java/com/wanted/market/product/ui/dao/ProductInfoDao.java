package com.wanted.market.product.ui.dao;

import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.product.ui.dto.request.QueryMineRequest;
import com.wanted.market.product.ui.dto.request.QueryRequest;
import com.wanted.market.product.ui.dto.response.DetailProductInfoResponse;
import com.wanted.market.product.ui.dto.response.SimpleProductInfoResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductInfoDao {
    List<SimpleProductInfoResponse> findAll(QueryRequest request);

    List<DetailProductInfoResponse> findAllWithOrders(QueryMineRequest request);

    Long count(QueryRequest request);

    Long countWithOrders(QueryMineRequest request);

    SimpleProductInfoResponse findById(Long id);

    DetailProductInfoResponse findByIdWithOrders(@Param("id") Long id, @Param("userId") Long userId);

    default SimpleProductInfoResponse findByIdOrThrow(Long id) throws NotFoundException {
        SimpleProductInfoResponse findProductInfo = findById(id);
        if (findProductInfo == null)
            throw new NotFoundException("no such product");
        return findProductInfo;
    }

    default DetailProductInfoResponse findWithOrdersByIdOrThrow(Long id, Long userId) throws NotFoundException {
        DetailProductInfoResponse findProductInfo = findByIdWithOrders(id, userId);
        if (findProductInfo == null)
            throw new NotFoundException("no such product");
        return findProductInfo;
    }
}
