package wanted.preonboard.market.domain.product;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private Integer sellerId;
    private String name;
    private Long price;
    private ProductState state;
}
