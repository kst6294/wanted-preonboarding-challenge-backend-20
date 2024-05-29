package com.wanted.preonboarding.backend20.domain.product.application;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductDetailDto;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductOutlineDto;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    void createProduct(ProductRequestDto dto, Member seller);
    void updateProduct(Long id, ProductRequestDto dto, Member seller);

    Page<ProductOutlineDto> searchAllProducts(Pageable pageable);
    ProductDetailDto searchProductById(Long id);
}
