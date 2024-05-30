package com.example.wantedmarketapi.service.impl;

import com.example.wantedmarketapi.converter.TradeConverter;
import com.example.wantedmarketapi.domain.enums.ProductStatus;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.response.TradeResponseDto.*;
import com.example.wantedmarketapi.repository.TradeRepository;
import com.example.wantedmarketapi.service.TradeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeQueryServiceImpl implements TradeQueryService {

    private final TradeRepository tradeRepository;

    @Override
    public List<GetProductResponse> getMyPurchaseProductList(Member member) {
        return TradeConverter.toGetProductResponseList(tradeRepository.findAllByPurchaserIdAndProduct_ProductStatus(member.getId(), ProductStatus.DONE));
    }

    @Override
    public List<GetProductResponse> getMyReservationProductList(Member member) {
        return TradeConverter.toGetProductResponseList(tradeRepository.findAllByPurchaserIdOrSellerIdAndProduct_ProductStatus(member.getId(), member.getId(), ProductStatus.RESERVATION));
    }

}
