package com.wanted.preonboarding.module.product.service;

import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;

public interface ProductQueryService {

    Sku createProduct(CreateProduct createProduct);

    Product doBooking(long productId);

}
