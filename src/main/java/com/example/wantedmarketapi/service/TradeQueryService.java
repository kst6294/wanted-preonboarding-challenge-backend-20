package com.example.wantedmarketapi.service;

import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.response.TradeResponseDto.*;

import java.util.List;

public interface TradeQueryService {

    List<GetProductResponse> getMyPurchaseProductList(Member member);

    List<GetProductResponse> getMyReservationProductList(Member member);
}
