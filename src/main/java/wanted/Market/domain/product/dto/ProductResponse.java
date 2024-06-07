package wanted.Market.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.Market.domain.product.entity.ProductStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String name;
    private int price;
    private ProductStatus status;
}
