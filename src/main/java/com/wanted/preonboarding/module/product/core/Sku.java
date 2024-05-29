package com.wanted.preonboarding.module.product.core;

import com.wanted.preonboarding.module.common.mapper.LastDomainIdProvider;
import com.wanted.preonboarding.module.product.enums.ProductStatus;

public interface Sku extends LastDomainIdProvider {

    Long getId();
    String getProductName();
    long getPrice();
    ProductStatus getProductStatus();
    String getSeller();
    int getQuantity();

}
