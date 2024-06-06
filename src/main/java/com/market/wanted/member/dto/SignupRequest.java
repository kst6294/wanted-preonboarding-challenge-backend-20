package com.market.wanted.member.dto;

import com.market.wanted.member.entity.Member;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class SignupRequest {

    private String username;
    private String password;
    private String name;

    @Builder
    public SignupRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
    }
}
