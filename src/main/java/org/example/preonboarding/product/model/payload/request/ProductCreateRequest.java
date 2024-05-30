package org.example.preonboarding.product.model.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import org.example.preonboarding.product.model.enums.ProductSellingStatus;
import org.example.preonboarding.product.model.enums.ProductType;


@Getter
public class ProductCreateRequest {

    @NotNull(message = "상품 타입은 필수입니다.")
    private final ProductType productType;

    @NotNull(message = "상품 판매상태는 필수입니다.")
    private final ProductSellingStatus productSellingStatus;

    @NotBlank(message = "상품 이름은 필수입니다.")
    private final String name;

    private final String description;

    @Positive(message = "상품 가격은 양수여야 합니다.")
    private final int price;

    @NotBlank(message = "판매자 id는 필수입니다.")
    private final String sellingUserId;

    @Builder
    private ProductCreateRequest(ProductType productType, ProductSellingStatus productSellingStatus, String name, String description, int price, String sellingUserId) {
        this.productType = productType;
        this.productSellingStatus = productSellingStatus;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sellingUserId = sellingUserId;
    }

}
