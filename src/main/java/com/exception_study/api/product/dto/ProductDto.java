package com.exception_study.api.product.dto;

import com.exception_study.api.product.entity.Product;
import com.exception_study.api.user_account.dto.UserAccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private int price;
    private String status;
    private int quantity;
    private UserAccountDto seller;


    public static ProductDto of(Long id, String name, int price, String status, int quantity, UserAccountDto seller) {
        return new ProductDto(id, name, price, status, quantity, seller);
    }

    public static ProductDto from(Product entity) {
        return ProductDto.of(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStatus(),
                entity.getQuantity(),
                UserAccountDto.from(entity.getSeller())
        );
    }

}