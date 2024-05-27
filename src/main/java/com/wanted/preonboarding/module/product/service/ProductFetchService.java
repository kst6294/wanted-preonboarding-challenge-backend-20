package com.wanted.preonboarding.module.product.service;

import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.filter.ItemFilter;
import org.springframework.data.domain.Pageable;

public interface ProductFetchService {

    Sku fetchProduct(long productId);

    CustomSlice<Sku> fetchProducts(ItemFilter filter, Pageable pageable);

}
