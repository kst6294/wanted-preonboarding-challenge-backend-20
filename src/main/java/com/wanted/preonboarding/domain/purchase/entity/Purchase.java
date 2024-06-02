package com.wanted.preonboarding.domain.purchase.entity;

import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 아이디

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 구매자

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product; // 상품

    @Column(nullable = false)
    @Min(0) @ColumnDefault("0")
    private Integer price; // 구매당시 가격

    @Enumerated(EnumType.STRING)
    private PurchaseState state; // 상태

    public void updateState(PurchaseState state) {
        this.state = state;
    }
}
