package wanted.preonboard.market.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Product {
    private Integer id;
    @JsonIgnore
    private Integer sellerId;
    private String name;
    private Long price;
    private ProductState state;
}
