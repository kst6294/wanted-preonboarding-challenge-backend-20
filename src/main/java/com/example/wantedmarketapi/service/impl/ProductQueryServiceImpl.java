package com.example.wantedmarketapi.service.impl;

import com.example.wantedmarketapi.converter.ProductConverter;
import com.example.wantedmarketapi.dto.response.ProductResponseDto.*;
import com.example.wantedmarketapi.repository.ProductRepository;
import com.example.wantedmarketapi.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    @Override
    public List<GetProductResponse> getProductList(){
        return ProductConverter.toGetProductListResponseList(productRepository.findAll());
    }

}
