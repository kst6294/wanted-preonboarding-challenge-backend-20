package com.example.wanted_market.domain;

import com.example.wanted_market.type.ERole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Builder
    public User(String email, String password, String nickname, ERole role){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }
}
