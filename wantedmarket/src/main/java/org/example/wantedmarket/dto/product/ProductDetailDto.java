package org.example.wantedmarket.dto.product;

import lombok.*;
import org.example.wantedmarket.status.ProductStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {

    private Long id;
    private String name;
    private Integer price;
    private ProductStatus status;
    private Long sellerId;

}
