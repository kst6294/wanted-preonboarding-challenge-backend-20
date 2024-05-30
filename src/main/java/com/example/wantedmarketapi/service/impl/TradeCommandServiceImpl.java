package com.example.wantedmarketapi.service.impl;

import com.example.wantedmarketapi.converter.TradeConverter;
import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.Trade;
import com.example.wantedmarketapi.domain.enums.ProductStatus;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.exception.GlobalErrorCode;
import com.example.wantedmarketapi.exception.custom.ProductException;
import com.example.wantedmarketapi.repository.ProductRepository;
import com.example.wantedmarketapi.repository.TradeRepository;
import com.example.wantedmarketapi.service.TradeCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeCommandServiceImpl implements TradeCommandService {

    private final TradeRepository tradeRepository;
    private final ProductRepository productRepository;

    public Trade createTrade(Member member, Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductException(GlobalErrorCode.PRODUCT_NOT_FOUND));
        product.setProductStatus(ProductStatus.RESERVATION);
        productRepository.save(product);

        return tradeRepository.save(TradeConverter.toTrade(member, product));
    }

}
