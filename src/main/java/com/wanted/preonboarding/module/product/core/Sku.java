package com.wanted.preonboarding.module.product.core;

import com.wanted.preonboarding.module.product.enums.ProductStatus;

public interface Sku {

    long getId();
    String getProductName();
    long getPrice();
    ProductStatus getProductStatus();
    String getSeller();

}
