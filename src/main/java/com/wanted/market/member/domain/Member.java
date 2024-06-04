package com.wanted.market.member.domain;

import com.wanted.market.member.domain.vo.MemberInfo;
import jakarta.persistence.*;

@Entity
@Table(name = "MEMBER", indexes = {@Index(name = "username_idx", columnList = "username", unique = true)})
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, unique = true, nullable = false)
    private String username;
    @Column(length = 50, nullable = false)
    private String password;

    protected Member() {
    }

    public Member(String username, String rawPassword, PasswordEncoder passwordEncoder) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be null or empty");
        if (rawPassword == null || rawPassword.isBlank())
            throw new IllegalArgumentException("Password cannot be null or empty");
        this.username = username;
        this.password = passwordEncoder.encode(rawPassword);
    }

    public MemberInfo login(String rawPassword, PasswordEncoder passwordEncoder) {
        if (matches(rawPassword, passwordEncoder)) {
            return getMemberInfo();
        }
        return null;
    }

    private MemberInfo getMemberInfo() {
        return new MemberInfo(this.id, this.username);
    }

    private boolean matches(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.password);
    }
}
