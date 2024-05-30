package wanted.preonboard.market.domain.Product;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private Integer sellerId;
    private String name;
    private Long price;
    private ProductState state;
}
