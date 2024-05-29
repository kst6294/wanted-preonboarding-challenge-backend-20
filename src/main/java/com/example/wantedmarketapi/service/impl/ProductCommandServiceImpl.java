package com.example.wantedmarketapi.service.impl;

import com.example.wantedmarketapi.converter.ProductConverter;
import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.request.ProductRequestDto.*;
import com.example.wantedmarketapi.repository.ProductRepository;
import com.example.wantedmarketapi.service.ProductCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Member member, CreateProductRequest request) {
        return productRepository.save(ProductConverter.toProduct(member, request));
    }

}
