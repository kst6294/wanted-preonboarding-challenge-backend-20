package wanted.preonboard.market.domain.dto;

import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;
import wanted.preonboard.market.domain.common.ProductState;

@Data
public class ProductUpdateDto {
    private JsonNullable<String> name;
    private JsonNullable<Long> price;
    private JsonNullable<ProductState> state;
}
