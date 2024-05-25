package com.wanted.preonboarding.module.user.entity;


import com.wanted.preonboarding.module.common.entity.BaseEntity;
import com.wanted.preonboarding.module.user.enums.MemberShip;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "USERS")
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

    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    @Column(name = "MEMBERSHIP", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberShip memberShip;


}
