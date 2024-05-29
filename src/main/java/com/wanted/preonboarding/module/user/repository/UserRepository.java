package com.wanted.preonboarding.module.user.repository;

import com.wanted.preonboarding.module.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

}
