package com.exception_study.api.product.entity;

import com.exception_study.api.user_account.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "quantity")
    private int quantity;

    @JoinColumn(name = "seller")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount seller;


    public static Product of(String name, int price, int quantity, UserAccount seller) {
        Product entity = new Product();
        entity.setName(name);
        entity.setPrice(price);
        entity.setStatus("판매중");
        entity.setQuantity(quantity);
        entity.setSeller(seller);
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && quantity == product.quantity && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(status, product.status) && Objects.equals(seller, product.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, status, quantity, seller);
    }
}
