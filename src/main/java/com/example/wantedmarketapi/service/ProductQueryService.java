package com.example.wantedmarketapi.service;

import com.example.wantedmarketapi.dto.response.ProductResponseDto.*;

import java.util.List;

public interface ProductQueryService {

    List<GetProductResponse> getProductList();

}
