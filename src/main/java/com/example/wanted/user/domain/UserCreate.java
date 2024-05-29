package com.example.wanted.user.domain;

import com.example.wanted.user.infrastucture.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {
    private final String account;
    private final String password;
    private final String name;

    @Builder
    public UserCreate(
            @JsonProperty("account") String account,
            @JsonProperty("password") String password,
            @JsonProperty("name") String name) {
        this.account = account;
        this.password = password;
        this.name = name;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .account(this.account)
                .password(this.password)
                .name(this.name)
                .role(Role.USER)
                .build();

    }
}
