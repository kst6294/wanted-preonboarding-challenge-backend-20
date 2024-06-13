package org.example.wantedmarket.dto.product;

import lombok.*;
import org.example.wantedmarket.status.ProductStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInfoDto {
    private Long productId;
    private String name;
    private Integer price;
    private Integer quantity;
    private ProductStatus status;

    @Override
    public String toString() {
        return "ProductInfoDto{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }

}
