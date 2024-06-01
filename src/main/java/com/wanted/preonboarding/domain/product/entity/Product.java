package com.wanted.preonboarding.domain.product.entity;

import com.wanted.preonboarding.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 판매자

    @Column(nullable = false)
    private String name; // 제품명

    @Column(nullable = false)
    @Min(0)
    private Integer price; // 가격

    @Column(nullable = false)
    @Min(0)
    private Integer quantity; // 재고
}
