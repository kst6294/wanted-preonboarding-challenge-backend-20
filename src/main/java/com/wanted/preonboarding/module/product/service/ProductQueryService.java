package com.wanted.preonboarding.module.product.service;

import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;

public interface ProductQueryService {

    Sku createProduct(CreateProduct createProduct);

}
