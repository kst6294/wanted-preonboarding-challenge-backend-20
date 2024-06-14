package com.wanted.market.order.service;

import com.wanted.market.common.http.dto.response.PageInfo;
import com.wanted.market.global.util.PageUtils;
import com.wanted.market.order.ui.dao.OrderInfoDao;
import com.wanted.market.order.ui.dto.request.QueryRequest;
import com.wanted.market.order.ui.dto.response.OrderInfosResponse;
import com.wanted.market.order.ui.dto.response.SimpleOrderInfoResponse;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QueryOrderService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final OrderInfoDao orderDao;

    public QueryOrderService(OrderInfoDao orderDao) {
        this.orderDao = orderDao;
    }

    @Timed("QueryOrderService.findAll")
    public OrderInfosResponse<SimpleOrderInfoResponse> findAll(QueryRequest request) {
        logger.info("start query orders: {}", request);
        List<SimpleOrderInfoResponse> content = orderDao.findAll(request);
        logger.info("found orders count: {}", content.size());
        PageInfo pageInfo = PageUtils.getPageInfo(orderDao::count, request);
        logger.info("found orders total count: {}", pageInfo.totalElements());
        return new OrderInfosResponse<>(content, pageInfo);
    }
}
