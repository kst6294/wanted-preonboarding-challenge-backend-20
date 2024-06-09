package org.example.wantedpreonboardingchallengebackend20.product.service;

import lombok.extern.slf4j.Slf4j;
import org.example.wantedpreonboardingchallengebackend20.product.entity.Product;
import org.example.wantedpreonboardingchallengebackend20.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public ProductService(ProductRepository productRepository, OrderService orderService) {
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product postProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(int productId) {
        return productRepository.findById(productId);
    }
}
