package com.wanted.market.user.domain;

import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String name;

    public User(String name){
        this.name = name;
    }
}
