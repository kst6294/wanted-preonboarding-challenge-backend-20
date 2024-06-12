package com.example.wantedmarketapi.infrastructure.user;

import com.example.wantedmarketapi.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);
}
