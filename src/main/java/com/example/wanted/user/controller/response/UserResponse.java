package com.example.wanted.user.controller.response;

import com.example.wanted.user.domain.User;
import com.example.wanted.user.infrastucture.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {
    private final String account;
    private final String name;

    @Builder
    public UserResponse(String account, String name) {
        this.account = account;
        this.name = name;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .account(user.getAccount())
                .name(user.getName())
                .build();
    }
}
