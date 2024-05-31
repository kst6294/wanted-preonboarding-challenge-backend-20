package wanted.preonboard.market.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;
import wanted.preonboard.market.domain.product.ProductState;

@Data
public class ProductUpdateDto {
    private JsonNullable<String> name;
    private JsonNullable<Long> price;
    @JsonIgnore
    private JsonNullable<ProductState> state;
}
