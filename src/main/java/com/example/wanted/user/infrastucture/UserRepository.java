package com.example.wanted.user.infrastucture;

import com.example.wanted.user.infrastucture.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByAccount(String account);
}
