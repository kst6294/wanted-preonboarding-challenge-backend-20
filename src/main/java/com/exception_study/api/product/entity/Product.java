package com.exception_study.api.product.entity;

import com.exception_study.api.user_account.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "status")
    private String status;

//    @Column(name = "quantity")
//    private int quantity;

    @JoinColumn(name = "seller")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount seller;




    public static Product of(String name, int price,UserAccount seller){
        Product entity = new Product();
        entity.setName(name);
        entity.setPrice(price);
        entity.setStatus("판매중");
//        entity.setQuantity(quantity);
        entity.setSeller(seller);
        return entity;
    }

}
