package com.example.wanted_market.repository;

import com.example.wanted_market.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
