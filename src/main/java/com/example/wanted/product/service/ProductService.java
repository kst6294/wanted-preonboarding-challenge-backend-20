package com.example.wanted.product.service;

import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.product.domain.Product;
import com.example.wanted.product.domain.ProductCreate;
import com.example.wanted.product.service.port.ProductRepository;
import com.example.wanted.product.service.response.ProductResponse;
import com.example.wanted.user.domain.User;
import com.example.wanted.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Builder
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Long register(ProductCreate productCreate, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("Product", userId)
        );

        Product product = Product.from(productCreate, user);
        product = productRepository.save(product);

        return product.getId();
    }

    public ProductResponse getById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product", id)
        );

        return ProductResponse.from(product);
    }

    public List<ProductResponse> getList() {
        List<Product> products = productRepository.find();

        return products
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }
}
