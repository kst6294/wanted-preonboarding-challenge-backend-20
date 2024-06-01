package com.example.wanted.user.controller.response;

import com.example.wanted.user.domain.User;
import com.example.wanted.user.infrastucture.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;
    private final String account;
    private final String name;

    @Builder
    public UserResponse(Long id,String account, String name) {
        this.id = id;
        this.account = account;
        this.name = name;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .name(user.getName())
                .build();
    }
}
