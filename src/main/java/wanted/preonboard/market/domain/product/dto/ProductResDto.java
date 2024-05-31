package wanted.preonboard.market.domain.product.dto;

import lombok.Getter;
import lombok.Setter;
import wanted.preonboard.market.domain.product.Product;

@Getter
@Setter
public class ProductResDto extends Product {
    private String sellerName;
}
