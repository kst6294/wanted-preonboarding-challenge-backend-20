package org.example.wantedmarket.dto.product;

import lombok.*;
import org.example.wantedmarket.dto.order.OrderInfoDto;
import org.example.wantedmarket.status.ProductStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {

    private Long productId;
    private String name;
    private Integer price;
    private ProductStatus status;
    private Long sellerId;
    private List<OrderInfoDto> transactionList = new ArrayList<>();

}
