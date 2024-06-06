package com.market.wanted.member.entity;

import com.market.wanted.common.entity.BaseEntity;
import com.market.wanted.order.entity.Order;
import com.market.wanted.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Builder
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Comment("회원id")
    private String username;
    private String password;
    private String role = "ROLE_USER";
    private String name;



    @OneToMany(mappedBy = "seller")
    private List<Product> products = new ArrayList<>();


    @OneToMany(mappedBy = "buyer")
    private List<Order> ordersByBuyer = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<Order> ordersBySeller = new ArrayList<>();


    public Member(String password, String name, String username) {
        this.password = password;
        this.name = name;
        this.username = username;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.addSeller(this);
    }

    public void addOrder(Order order) {
        this.ordersByBuyer.add(order);
        order.addBuyer(this);
    }



}
