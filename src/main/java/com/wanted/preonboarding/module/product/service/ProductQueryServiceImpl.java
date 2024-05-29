package com.wanted.preonboarding.module.product.service;


import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.mapper.ProductMapper;
import com.wanted.preonboarding.module.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductQueryServiceImpl implements ProductQueryService{

    private final ProductFindService productFindService;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public Sku createProduct(CreateProduct createProduct) {
        Product product = productMapper.toProduct(createProduct);
        Product savedProduct = productRepository.save(product);
        return productMapper.toSku(savedProduct);
    }

    @Override
    public Product doBooking(long productId) {
        Product product = productFindService.fetchProductEntity(productId);
        product.doBooking();
        return product;
    }

    @Override
    public void outOfStock(long productId) {
        Product product = productFindService.fetchProductEntity(productId);
        product.outOfStock();
    }

}
