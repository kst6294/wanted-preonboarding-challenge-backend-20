package com.market.wanted.member.entity;

import com.market.wanted.common.entity.BaseEntity;
import com.market.wanted.order.entity.Order;
import com.market.wanted.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;
    private String name;


    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "buyer")
    private List<Product> products = new ArrayList<>();


    @OneToMany(mappedBy = "seller")
    private List<Order> orders = new ArrayList<>();

    public Member(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.addSeller(this);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.addBuyer(this);
    }



}
