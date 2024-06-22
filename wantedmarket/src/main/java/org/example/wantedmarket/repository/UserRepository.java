package org.example.wantedmarket.repository;

import org.example.wantedmarket.domain.User;

public interface UserRepository {

    User getByUsername(String username);

    Boolean existsByUsername(String username);

}
