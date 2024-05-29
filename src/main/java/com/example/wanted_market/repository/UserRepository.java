package com.example.wanted_market.repository;

import com.example.wanted_market.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일 중복 확인
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
