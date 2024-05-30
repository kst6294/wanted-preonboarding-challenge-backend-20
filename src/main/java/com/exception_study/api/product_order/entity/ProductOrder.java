package com.exception_study.api.product_order.entity;

import com.exception_study.api.product.entity.Product;
import com.exception_study.api.user_account.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "product")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @Column(name = "price")
    private int price;

    @JoinColumn(name = "seller")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount seller;

    @JoinColumn(name = "buyer")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount buyer;

    @Column(name = "seller_status")
    private String sellerStatus;

    @Column(name = "buyer_status")
    private String buyerStatus;


    public static ProductOrder of(Long id, Product product, int price, UserAccount seller, UserAccount buyer, String sellerStatus, String buyerStatus){
        return new ProductOrder(id,product,price,seller,buyer,sellerStatus,buyerStatus);
    }

}
