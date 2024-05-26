package com.wanted.preonboarding.module.product.core;

import com.wanted.preonboarding.module.product.enums.ProductStatus;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class BaseSku implements Sku{

    private long id;
    private String productName;
    private long price;
    private ProductStatus productStatus;
    private String seller;

}
