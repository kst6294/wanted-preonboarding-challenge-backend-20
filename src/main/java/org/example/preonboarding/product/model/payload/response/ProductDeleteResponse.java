package org.example.preonboarding.product.model.payload.response;

import lombok.Builder;
import lombok.Getter;
import org.example.preonboarding.product.model.enums.ProductSellingStatus;
import org.example.preonboarding.product.model.enums.ProductType;

@Getter
public class ProductDeleteResponse {
    private final Long id;
    private final String productNumber;
    private final ProductSellingStatus productSellingStatus;
    private final String name;

    @Builder
    private ProductDeleteResponse(Long id, String productNumber, ProductSellingStatus productSellingStatus, String name) {
        this.id = id;
        this.productNumber = productNumber;
        this.productSellingStatus = productSellingStatus;
        this.name = name;
    }

}
