package com.example.wantedmarketapi.domain.member;


import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.Trade;
import com.example.wantedmarketapi.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Embedded
    private Password password;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "purchaser", cascade = CascadeType.ALL)
    private List<Trade> purchaserList = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Trade> sellerList = new ArrayList<>();

}
