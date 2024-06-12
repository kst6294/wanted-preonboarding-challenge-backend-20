package com.example.wantedmarketapi.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCommand {
    private final String username;
    private final String email;
    private final String password;

    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
