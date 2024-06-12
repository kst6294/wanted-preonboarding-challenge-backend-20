package com.example.wantedmarketapi.domain.user;

import lombok.Getter;

@Getter
public class UserInfo {
    private final Long id;
    private final String username;
    private final String email;

    public UserInfo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

}
