package com.wanted.market.order.service;

import com.wanted.market.common.http.dto.request.PageRequest;
import com.wanted.market.common.http.dto.response.PageInfo;
import com.wanted.market.order.ui.dao.OrderInfoDao;
import com.wanted.market.order.ui.dto.request.QueryRequest;
import com.wanted.market.order.ui.dto.response.OrderInfosResponse;
import com.wanted.market.order.ui.dto.response.SimpleOrderInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
public class QueryOrderService {
    private final OrderInfoDao orderDao;

    public QueryOrderService(OrderInfoDao orderDao) {
        this.orderDao = orderDao;
    }

    public OrderInfosResponse<SimpleOrderInfoResponse> findAll(QueryRequest request) {
        List<SimpleOrderInfoResponse> content = orderDao.findAll(request);
        PageInfo pageInfo = getPageInfo(orderDao::count, request);
        return new OrderInfosResponse<>(content, pageInfo);
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
