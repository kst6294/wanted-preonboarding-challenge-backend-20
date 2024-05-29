package com.market.wanted.member.entity;

import com.market.wanted.common.entity.BaseEntity;
import com.market.wanted.order.entity.Order;
import com.market.wanted.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;
    private String userName;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "buyer")
    private List<Product> products = new ArrayList<>();


    @OneToMany(mappedBy = "seller")
    private List<Order> orders = new ArrayList<>();

    public Member(String password, String userName, String email) {
        this.password = password;
        this.userName = userName;
        this.email = email;
    }
}
