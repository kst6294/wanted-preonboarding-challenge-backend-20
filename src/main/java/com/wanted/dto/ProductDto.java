package com.wanted.dto;

import com.wanted.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long product_id;

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 1, message = "가격은 0보다 커야합니다.")
    private Long price;

    @NotNull(message = "재고수량은 필수입니다.")
    @Min(value = 1, message = "재고수량은 0보다 커야합니다.")
    private Long stock_quantity;

    private String status;

    public ProductDto(Product product){
        this.product_id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock_quantity = product.getStockQuantity();
        this.status = product.getStatus().name();
    }


}
