package com.wanted.market.product.service;

import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.common.http.dto.response.PageInfo;
import com.wanted.market.product.ui.dao.ProductInfoDao;
import com.wanted.market.product.ui.dto.request.QueryRequest;
import com.wanted.market.product.ui.dto.response.ProductInfoResponse;
import com.wanted.market.product.ui.dto.response.ProductInfosResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QueryProductService {
    private final ProductInfoDao productDao;

    public QueryProductService(ProductInfoDao productDao) {
        this.productDao = productDao;
    }

    public ProductInfosResponse findAll(QueryRequest request) {
        List<ProductInfoResponse> content = productDao.findAll(request);
        Integer pageNumber = request.getPageNumber();
        Integer pageSize = request.getPageSize();
        Long totalElements = productDao.count(request);
        Integer totalPages = (int) Math.ceil((double) totalElements / (double) pageSize);
        boolean isFirst = pageNumber <= 1;
        boolean isLast = pageNumber >= totalPages;
        PageInfo pageInfo = new PageInfo(pageNumber, pageSize, totalPages, totalElements, isFirst, isLast);
        return new ProductInfosResponse(content, pageInfo);
    }

    public ProductInfoResponse findById(Long id) throws NotFoundException {
        return productDao.findByIdOrThrow(id);
    }
}
