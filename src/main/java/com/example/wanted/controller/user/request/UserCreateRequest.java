package com.example.wanted.controller.user.request;

import com.example.wanted.domain.user.ENUM.Role;
import com.example.wanted.domain.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateRequest {
    private final String account;
    private final String password;
    private final String name;

    @Builder
    public UserCreateRequest(
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
