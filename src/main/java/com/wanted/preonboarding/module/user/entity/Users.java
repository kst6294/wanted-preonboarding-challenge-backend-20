package com.wanted.preonboarding.module.user.entity;


import com.wanted.preonboarding.module.common.entity.BaseEntity;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.enums.MemberShip;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "USERS")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private long id;

    @Column(name = "PHONE_NUMBER", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Setter
    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    @Column(name = "MEMBERSHIP", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberShip memberShip;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Product> products;


}
