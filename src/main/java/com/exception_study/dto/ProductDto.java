package com.exception_study.dto;

import com.exception_study.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto{
    private int id;
    private String name;
    private int price;
    private String status;
    private String seller;
    private String buyer;


    public static ProductDto of(int id, String name, int price, String status,String seller, String buyer){
        return new ProductDto(id,name,price,status,seller,buyer);
    }
    public static ProductDto from(Product entity){
        return ProductDto.of(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStatus(),
                entity.getSeller(),
                entity.getBuyer()
        );
    }

}