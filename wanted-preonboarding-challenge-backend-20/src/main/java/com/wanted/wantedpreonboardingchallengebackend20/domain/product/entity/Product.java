package com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity;

import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer price;
    private Integer quantity;
    @Enumerated(value=EnumType.STRING)
    private ProductState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    public void assignUser(User user){
        this.user=user;
        user.getProductList().add(this);
    }
}
