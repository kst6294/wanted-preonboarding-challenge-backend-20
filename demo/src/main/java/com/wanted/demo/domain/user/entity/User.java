package com.wanted.demo.domain.user.entity;

import com.wanted.demo.domain.baseEntity.BaseEntity;
import com.wanted.demo.domain.product.entity.Product;
import com.wanted.demo.domain.statements.entity.Statements;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Statements> statements = new ArrayList<>();

    @Builder
    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

}
