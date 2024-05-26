package com.wanted.preonboarding.module.product.mapper;

import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;

public interface ProductMapper {

    Product toProduct(CreateProduct createProduct);

    Sku toSku(Product product);
}
