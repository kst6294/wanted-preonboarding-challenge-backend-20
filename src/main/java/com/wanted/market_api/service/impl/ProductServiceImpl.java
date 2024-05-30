package com.wanted.market_api.service.impl;

import com.wanted.market_api.constant.ErrorCode;
import com.wanted.market_api.constant.ProductStatus;
import com.wanted.market_api.dto.ApiResponse;
import com.wanted.market_api.dto.request.product.ProductCreateRequestDto;
import com.wanted.market_api.dto.response.product.PagingProductResponseDto;
import com.wanted.market_api.dto.response.product.ProductResponseDto;
import com.wanted.market_api.entity.Member;
import com.wanted.market_api.entity.Product;
import com.wanted.market_api.exception.BaseException;
import com.wanted.market_api.repository.ProductRepository;
import com.wanted.market_api.repository.ProductRepositoryCustom;
import com.wanted.market_api.service.MemberService;
import com.wanted.market_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductRepositoryCustom productRepositoryCustom;

    private final MemberService memberService;

    @Override
    public ApiResponse createProduct(ProductCreateRequestDto productCreateRequestDto, long memberId) {
        Member member = memberService.findById(memberId);
        productRepository.save(
                Product.builder()
                        .name(productCreateRequestDto.getName())
                        .price(productCreateRequestDto.getPrice())
                        .member(member)
                        .productStatus(ProductStatus.IN_SALE)
                        .build()
        );
        return new ApiResponse<>();
    }

    @Override
    public ProductResponseDto getProduct(long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productStatus(product.getProductStatus())
                .build();
    }

    @Override
    public PagingProductResponseDto getProducts(Pageable pageable) {
        Page<Product> pageProducts = productRepositoryCustom.findAllProducts(pageable);
        return PagingProductResponseDto.builder()
                .totalPages(pageProducts.getTotalPages())
                .totalElements(pageProducts.getTotalElements())
                .pageNumber(pageProducts.getPageable().getPageNumber())
                .products(pageProducts.getContent().stream().map(
                        product -> ProductResponseDto.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .productStatus(product.getProductStatus())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
