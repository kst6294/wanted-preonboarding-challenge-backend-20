package com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity;

import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Product> productList=new ArrayList<>();

}
