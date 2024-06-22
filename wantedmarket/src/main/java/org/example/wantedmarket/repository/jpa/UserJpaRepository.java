package org.example.wantedmarket.repository.jpa;

import org.example.wantedmarket.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Boolean existsByUsername(String username);

}
