package com.wanted.market.product.service;

import com.wanted.market.product.domain.Product;
import com.wanted.market.product.dto.ProductRequestDto;
import com.wanted.market.product.dto.ProductResponseDto;
import com.wanted.market.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServcieImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServcieImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto registProduct(ProductRequestDto productRequestDto) {
        Product product = productRequestDto.toEntity();
        Product savedProduct = productRepository.save(product);
        return ProductResponseDto.createFromEntity(savedProduct);
    }


    @Override
    public ProductResponseDto findById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("제품을 찾을 수 없습니다."));
        return ProductResponseDto.createFromEntity(product);
    }

    @Override
    public List<ProductResponseDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductResponseDto::createFromEntity)
                .collect(Collectors.toList());
    }
}
