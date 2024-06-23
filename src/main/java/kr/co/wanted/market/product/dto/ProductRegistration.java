package kr.co.wanted.market.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.wanted.market.product.entity.Product;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

import static kr.co.wanted.market.common.global.constants.Constant.*;

@Builder
public record ProductRegistration(Long id,
                                  Long sellerId,

                                  @NotBlank(message = "필수입니다.")
                                  String name,

                                  @NotNull(message = "필수입니다.")
                                  @Range(max = PRODUCT_PRICE_MAX)
                                  Long price,

                                  @NotNull(message = "필수입니다.")
                                  @Range(min = PRODUCT_QUANTITY_MIN, max = PRODUCT_QUANTITY_MAX)
                                  Long quantity) {

    public static ProductRegistration fromEntity(Product product) {

        return new ProductRegistration(
                product.getId(),
                product.getSeller().getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity()
        );
    }
}
