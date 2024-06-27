package com.wanted.market.product.dto;

import com.wanted.market.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequestDto {
    private String productName;
    private Integer price;
    private String productStatus;
    private Integer quantity;

    public void updateProduct(Product product) {
        if (productName != null) {
            product.updateName(productName);
        }
        if (price != null) {
            product.updatePrice(price);
        }
        if (quantity != null) {
            product.updateQuantity(quantity);
        }
    }

}
