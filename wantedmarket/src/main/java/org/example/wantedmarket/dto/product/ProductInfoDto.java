package org.example.wantedmarket.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.wantedmarket.status.ProductStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDto {
    private Long id;
    private String name;
    private Integer price;
    private ProductStatus status;
}
