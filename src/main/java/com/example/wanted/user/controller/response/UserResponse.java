package com.example.wanted.user.controller.response;

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

    public static UserResponse from(UserEntity userEntity) {
        return UserResponse.builder()
                .account(userEntity.getAccount())
                .name(userEntity.getName())
                .build();
    }
}
