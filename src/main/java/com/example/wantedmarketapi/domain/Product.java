package com.example.wantedmarketapi.domain;


import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.domain.common.BaseEntity;
import com.example.wantedmarketapi.domain.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus productStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Trade> tradeList = new ArrayList<>();

}
