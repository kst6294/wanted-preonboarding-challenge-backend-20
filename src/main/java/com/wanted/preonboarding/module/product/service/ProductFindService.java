package com.wanted.preonboarding.module.product.service;

import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import org.springframework.data.domain.Pageable;

public interface ProductFindService {

    Sku fetchProduct(long productId);

    Product fetchProductEntity(long productId);
    Product fetchProductEntity(long productId, String email);

    CustomSlice<Sku> fetchProducts(ItemFilter filter, Pageable pageable);


}
