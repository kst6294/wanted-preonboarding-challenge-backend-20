package com.wanted.market.product.service;

import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.common.http.dto.request.PageRequest;
import com.wanted.market.common.http.dto.response.PageInfo;
import com.wanted.market.product.ui.dao.ProductInfoDao;
import com.wanted.market.product.ui.dto.request.QueryMineRequest;
import com.wanted.market.product.ui.dto.request.QueryRequest;
import com.wanted.market.product.ui.dto.response.DetailProductInfoResponse;
import com.wanted.market.product.ui.dto.response.ProductInfosResponse;
import com.wanted.market.product.ui.dto.response.SimpleProductInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
public class QueryProductService {
    private final ProductInfoDao productDao;

    public QueryProductService(ProductInfoDao productDao) {
        this.productDao = productDao;
    }

    public ProductInfosResponse<SimpleProductInfoResponse> findAll(QueryRequest request) {
        List<SimpleProductInfoResponse> content = productDao.findAll(request);
        PageInfo pageInfo = getPageInfo(productDao::count, request);
        return new ProductInfosResponse<>(content, pageInfo);
    }

    public ProductInfosResponse<DetailProductInfoResponse> findAllWithOrders(QueryMineRequest request) {
        List<DetailProductInfoResponse> content = productDao.findAllWithOrders(request);
        PageInfo pageInfo = getPageInfo(productDao::countWithOrders, request);
        return new ProductInfosResponse<>(content, pageInfo);
    }

    public DetailProductInfoResponse findById(Long id, Long userId) throws NotFoundException {
        if(userId == null){
            SimpleProductInfoResponse info = productDao.findByIdOrThrow(id);
            return DetailProductInfoResponse.createWithEmptyOrders(info);
        }
        return productDao.findWithOrdersByIdOrThrow(id, userId);
    }

    private <T extends PageRequest> PageInfo getPageInfo(Function<T, Long> countQuery, T request) {
        Integer pageNumber = request.getPageNumber();
        Integer pageSize = request.getPageSize();
        Long totalElements = countQuery.apply(request);
        Integer totalPages = (int) Math.ceil((double) totalElements / (double) pageSize);
        boolean isFirst = pageNumber <= 1;
        boolean isLast = pageNumber >= totalPages;
        return new PageInfo(pageNumber, pageSize, totalPages, totalElements, isFirst, isLast);
    }
}
