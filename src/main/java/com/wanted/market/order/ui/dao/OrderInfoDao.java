package com.wanted.market.order.ui.dao;

import com.wanted.market.common.http.dto.request.PageRequest;
import com.wanted.market.order.ui.dto.response.OrderInfoResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoDao {
    List<OrderInfoResponse> findAll(PageRequest pageRequest);
}
