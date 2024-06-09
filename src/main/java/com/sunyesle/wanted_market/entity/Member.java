package com.sunyesle.wanted_market.entity;

import com.sunyesle.wanted_market.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String name, String email, String password, PasswordEncoder passwordEncoder) {
        this.name = name;
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.role = Role.USER;
    }
}
