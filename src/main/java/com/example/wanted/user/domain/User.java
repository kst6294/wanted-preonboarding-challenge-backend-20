package com.example.wanted.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String name;
    private String account;
    private String password;
    private Role role;

    @Builder
    public User(Long id, String name, String account, String password, Role role) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.role = role;
    }

    public static User from(UserCreate userCreate) {
        return User.builder()
                .name(userCreate.getName())
                .account(userCreate.getAccount())
                .password(userCreate.getPassword())
                .role(Role.USER)
                .build();
    }
}
