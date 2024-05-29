package com.example.wanted.user.service.port;

import com.example.wanted.user.domain.User;
import com.example.wanted.user.infrastucture.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByAccount(String account);
}
