package com.wanted.preonboarding.module.product.mapper;

import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper{

    @Override
    public Product toProduct(CreateProduct createProduct) {
        Product product = Product.builder()
                .productName(createProduct.getProductName())
                .productStatus(ProductStatus.ON_STOCK)
                .price(createProduct.getPrice())
                .quantity(createProduct.getQuantity())
                .build();

        product.setSeller(createProduct.getUsers());
        return product;
    }

    @Override
    public BaseSku toSku(Product product) {
        return BaseSku.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productStatus(product.getProductStatus())
                .seller(product.getSeller().getEmail())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }
}
