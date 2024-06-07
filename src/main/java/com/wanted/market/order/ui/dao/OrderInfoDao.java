package com.wanted.market.order.ui.dao;

import com.wanted.market.order.ui.dto.request.QueryRequest;
import com.wanted.market.order.ui.dto.response.SimpleOrderInfoResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoDao {
    List<SimpleOrderInfoResponse> findAll(QueryRequest request);

    Long count(QueryRequest request);
}
