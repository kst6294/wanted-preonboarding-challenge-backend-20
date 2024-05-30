package com.exception_study.api.product_order.dto;

import com.exception_study.api.product.dto.ProductDto;
import com.exception_study.api.product_order.entity.ProductOrder;
import com.exception_study.api.user_account.dto.UserAccountDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductOrderDto {
    private Long id;

    private ProductDto product;

    private int price;

    private UserAccountDto seller;

    private UserAccountDto buyer;

    private String sellerStatus;

    private String buyerStatus;

    public static ProductOrderDto of(
            Long id,
            ProductDto product,
            int price,
            UserAccountDto seller,
            UserAccountDto buyer,
            String sellerStatus,
            String buyerStatus){
        return new ProductOrderDto(id, product, price, seller, buyer, sellerStatus, buyerStatus);
    }
    public static ProductOrderDto of(
            ProductDto product,
            int price,
            UserAccountDto seller,
            UserAccountDto buyer,
            String sellerStatus,
            String buyerStatus){
        return ProductOrderDto.of(null, product, price, seller, buyer, sellerStatus, buyerStatus);
    }

    public static ProductOrderDto from(ProductOrder entity){
        return ProductOrderDto.of(
                entity.getId(),
                ProductDto.from(entity.getProduct()),
                entity.getPrice(),
                UserAccountDto.from(entity.getSeller()),
                UserAccountDto.from(entity.getBuyer()),
                entity.getSellerStatus(),
                entity.getBuyerStatus()
                );
    }
}
