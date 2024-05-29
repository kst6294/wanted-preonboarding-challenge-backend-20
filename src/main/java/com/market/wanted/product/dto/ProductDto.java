package com.market.wanted.product.dto;

import com.market.wanted.product.entity.Product;
import com.market.wanted.product.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long productId;
    private String productName;
    private long price;
    private ProductStatus status;

}
