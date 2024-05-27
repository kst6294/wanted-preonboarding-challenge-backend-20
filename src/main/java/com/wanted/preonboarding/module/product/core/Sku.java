package com.wanted.preonboarding.module.product.core;

import com.wanted.preonboarding.module.common.mapper.CursorValueProvider;
import com.wanted.preonboarding.module.product.enums.ProductStatus;

public interface Sku extends CursorValueProvider {

    Long getId();
    String getProductName();
    long getPrice();
    ProductStatus getProductStatus();
    String getSeller();

}
