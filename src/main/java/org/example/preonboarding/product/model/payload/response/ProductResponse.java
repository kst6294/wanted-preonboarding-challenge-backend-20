package org.example.preonboarding.product.model.payload.response;

import lombok.Builder;
import lombok.Getter;
import org.example.preonboarding.product.model.enums.ProductSellingStatus;
import org.example.preonboarding.product.model.enums.ProductType;

@Getter
public class ProductResponse {
    private final Long id;
    private final String productNumber;
    private final ProductType productType;
    private final ProductSellingStatus productSellingStatus;
    private final String description;
    private final String name;
    private final int price;

    @Builder
    private ProductResponse(Long id, String productNumber, ProductType productType, ProductSellingStatus productSellingStatus, String description, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.productType = productType;
        this.productSellingStatus = productSellingStatus;
        this.description = description;
        this.name = name;
        this.price = price;
    }
}
