package org.example.wantedmarket.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.wantedmarket.dto.order.OrderResponse;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductDetailResponse {

    private ProductResponse product;
    private List<OrderResponse> transactions;
}
