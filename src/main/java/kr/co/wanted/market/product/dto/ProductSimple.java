package kr.co.wanted.market.product.dto;

import kr.co.wanted.market.product.entity.Product;
import kr.co.wanted.market.product.enums.ProductState;

public record ProductSimple(Long id,
                            String name,
                            Long price,
                            ProductState state) {

    public static ProductSimple fromEntity(Product product) {

        return new ProductSimple(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getState()
        );
    }
}
