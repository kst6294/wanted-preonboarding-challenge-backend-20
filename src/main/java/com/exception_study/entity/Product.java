package com.exception_study.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "status")
    private String status;
    @Column(name = "seller")
    private String seller;
    @Column(name = "buyer")
    private String buyer;



    public static Product of(String name, int price, String seller){
        Product entity = new Product();
        entity.setName(name);
        entity.setPrice(price);
        entity.setSeller(seller);
        entity.setStatus("판매중");
        entity.setBuyer(null);
        return entity;
    }

}
