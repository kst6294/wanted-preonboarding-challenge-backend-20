package com.example.wantedmarketapi.domain.user;

public interface UserReader {

    User getUser(Long userId);

    User getUser(String username);

}
