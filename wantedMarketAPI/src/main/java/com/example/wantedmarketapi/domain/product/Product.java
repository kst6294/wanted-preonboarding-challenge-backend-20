package com.example.wantedmarketapi.domain.product;

import com.example.wantedmarketapi.common.exception.InvalidParamException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(indexes = {@Index(name = "user_id_idx", columnList = "userId")})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        PREPARE("준비중"),
        ON_SALE("판매중"),
        RESERVED("예약중"),
        END_OF_SALE("완료");

        private final String value;
    }

    @Builder
    public Product(String name, Long userId, Integer price) {
        if (name == null || name.length() == 0) throw new InvalidParamException("empty product name");
        if (userId == null || userId <= 0) throw new InvalidParamException("invalid userId");
        if (price == null || price <= 0) throw new InvalidParamException("invalid price");

        this.name = name;
        this.userId = userId;
        this.price = price;
        this.status = Status.PREPARE;
    }

    protected Product() {

    }

}
