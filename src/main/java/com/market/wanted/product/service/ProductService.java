package com.market.wanted.product.service;

import com.market.wanted.product.dto.ProductDto;
import com.market.wanted.product.entity.Product;
import com.market.wanted.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto findDtoById(Long productId) {
        Product findProduct = productRepository.findById(productId).orElse(null);
        return new ProductDto(findProduct.getId(), findProduct.getProductName(), findProduct.getPrice(), findProduct.getStatus());
    }

    public

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto(product.getId(), product.getProductName(), product.getPrice(), product.getStatus());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }
}
