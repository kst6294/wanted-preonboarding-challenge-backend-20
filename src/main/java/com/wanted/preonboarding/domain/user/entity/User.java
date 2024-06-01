package com.wanted.preonboarding.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자 아이디

    @Column(unique = true, nullable = false)
    @Size(max = 320) // 최대길이
    private String email; // 사용자 이메일

    @Column(nullable = false)
    private String password; // 사용자 비밀번호

    @Column(nullable = false)
    @Size(max = 20)
    private String nickname; // 사용자 닉네임
}
