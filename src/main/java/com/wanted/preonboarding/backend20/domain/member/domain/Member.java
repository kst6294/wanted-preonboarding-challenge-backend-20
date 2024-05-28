package com.wanted.preonboarding.backend20.domain.member.domain;

import com.wanted.preonboarding.backend20.domain.member.dto.SignUpDto;
import com.wanted.preonboarding.backend20.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique=true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @ElementCollection
    private List<String> roles = new ArrayList<>();

    @Builder
    public Member(SignUpDto signUpDto, PasswordEncoder passwordEncoder) {
        this.email = signUpDto.getEmail();
        this.password = passwordEncoder.encode(signUpDto.getPassword());
        this.name = signUpDto.getName();
        this.roles = Collections.singletonList("ROLE_USER");
    }
}
