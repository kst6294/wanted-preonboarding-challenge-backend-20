package wanted.preonboard.market.domain.entity;

import lombok.Data;
import wanted.preonboard.market.domain.common.ProductState;

@Data
public class Product {
    private Long id;
    private Long sellerId;
    private String name;
    private double price;
    private ProductState state;
}
