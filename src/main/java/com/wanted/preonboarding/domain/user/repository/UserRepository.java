package com.wanted.preonboarding.domain.user.repository;

import com.wanted.preonboarding.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
