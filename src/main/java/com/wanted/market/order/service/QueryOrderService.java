package com.wanted.market.order.service;

import com.wanted.market.common.http.dto.response.PageInfo;
import com.wanted.market.global.util.PageUtils;
import com.wanted.market.order.ui.dao.OrderInfoDao;
import com.wanted.market.order.ui.dto.request.QueryRequest;
import com.wanted.market.order.ui.dto.response.OrderInfosResponse;
import com.wanted.market.order.ui.dto.response.SimpleOrderInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QueryOrderService {
    private final OrderInfoDao orderDao;

    public QueryOrderService(OrderInfoDao orderDao) {
        this.orderDao = orderDao;
    }

    public OrderInfosResponse<SimpleOrderInfoResponse> findAll(QueryRequest request) {
        List<SimpleOrderInfoResponse> content = orderDao.findAll(request);
        PageInfo pageInfo = PageUtils.getPageInfo(orderDao::count, request);
        return new OrderInfosResponse<>(content, pageInfo);
    }
}
