package com.example.wantedmarketapi.service;

import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.request.ProductRequestDto.*;

public interface ProductCommandService {

    Product createProduct(Member member, CreateProductRequest request);

    Product setProductStatus(Member member, Long productId);

}
