package com.wanted.preonboarding.backend20.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.preonboarding.backend20.domain.product.domain.Product;
import com.wanted.preonboarding.backend20.domain.product.enums.ProductStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailDto {
    private Long id;
    private String name;
    private int price;
    private String description;
    private ProductStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime salesStartDate;

    @Builder
    public ProductDetailDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.status = product.getStatus();
        this.salesStartDate = product.getCreatedDate();
    }
}
