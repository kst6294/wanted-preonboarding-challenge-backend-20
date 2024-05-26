package com.wanted.preonboarding.module.product.core;

import com.wanted.preonboarding.module.product.enums.ProductStatus;

public interface Sku {

    String getProductName();
    long getPrice();
    ProductStatus getProductStatus();

}
