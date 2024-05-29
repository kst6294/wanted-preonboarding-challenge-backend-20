package com.example.wantedmarketapi.converter;

import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.enums.ProductStatus;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.request.ProductRequestDto.*;
import com.example.wantedmarketapi.dto.response.ProductResponseDto.*;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public static Product toProduct(Member member, CreateProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .productStatus(ProductStatus.SALE)
                .member(member)
                .build();
    }

    public static CreateProductResponse toCreateProductResponse(Product product) {
        return CreateProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .build();
    }

}
