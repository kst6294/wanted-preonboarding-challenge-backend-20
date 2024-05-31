package com.exception_study.api.product_order.entity;

import com.exception_study.api.product.entity.Product;
import com.exception_study.api.user_account.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrder that = (ProductOrder) o;
        return price == that.price && Objects.equals(id, that.id) && Objects.equals(product, that.product) && Objects.equals(seller, that.seller) && Objects.equals(buyer, that.buyer) && Objects.equals(sellerStatus, that.sellerStatus) && Objects.equals(buyerStatus, that.buyerStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, price, seller, buyer, sellerStatus, buyerStatus);
    }
}
