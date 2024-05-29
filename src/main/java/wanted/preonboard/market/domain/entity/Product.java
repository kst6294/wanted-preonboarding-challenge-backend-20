package wanted.preonboard.market.domain.entity;

import lombok.Data;
import wanted.preonboard.market.domain.common.ProductState;

@Data
public class Product {
    private Integer id;
    private Integer sellerId;
    private String name;
    private Long price;
    private ProductState state;
}
