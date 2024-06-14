package com.wanted.market.product.service;

import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.common.http.dto.response.PageInfo;
import com.wanted.market.global.util.PageUtils;
import com.wanted.market.product.ui.dao.ProductInfoDao;
import com.wanted.market.product.ui.dto.request.QueryMineRequest;
import com.wanted.market.product.ui.dto.request.QueryRequest;
import com.wanted.market.product.ui.dto.response.DetailProductInfoResponse;
import com.wanted.market.product.ui.dto.response.ProductInfosResponse;
import com.wanted.market.product.ui.dto.response.SimpleProductInfoResponse;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QueryProductService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProductInfoDao productDao;

    public QueryProductService(ProductInfoDao productDao) {
        this.productDao = productDao;
    }

    @Timed("QueryProductService.findAll")
    public ProductInfosResponse<SimpleProductInfoResponse> findAll(QueryRequest request) {
        logger.info("start query products: {}", request);
        List<SimpleProductInfoResponse> content = productDao.findAll(request);
        logger.info("found products count: {}", content.size());
        PageInfo pageInfo = PageUtils.getPageInfo(productDao::count, request);
        logger.info("found products total count: {}", pageInfo.totalElements());
        return new ProductInfosResponse<>(content, pageInfo);
    }

    @Timed("QueryProductService.findAllWithOrders")
    public ProductInfosResponse<DetailProductInfoResponse> findAllWithOrders(QueryMineRequest request) {
        logger.info("start query my products: {}", request);
        List<DetailProductInfoResponse> content = productDao.findAllWithOrders(request);
        logger.info("found my products count: {}", content.size());
        PageInfo pageInfo = PageUtils.getPageInfo(productDao::countWithOrders, request);
        logger.info("found my products total count: {}", pageInfo.totalElements());
        return new ProductInfosResponse<>(content, pageInfo);
    }

    @Timed("QueryProductService.findById")
    public DetailProductInfoResponse findById(Long id, Long userId) throws NotFoundException {
        logger.info("start query a product: {}", id);
        if (userId == null) {
            logger.info("offline user, start simple query product");
            SimpleProductInfoResponse info = productDao.findByIdOrThrow(id);
            return DetailProductInfoResponse.createWithEmptyOrders(info);
        }
        logger.info("login user, start detail query product");
        return productDao.findWithOrdersByIdOrThrow(id, userId);
    }
}
