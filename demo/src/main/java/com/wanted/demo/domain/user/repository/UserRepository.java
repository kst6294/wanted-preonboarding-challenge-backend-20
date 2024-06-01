package com.wanted.demo.domain.user.repository;

import com.wanted.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //이메일 중복
    boolean existsByEmail(String email);


    //로그인
    @Query("select u from User u where u.email = :email")
    Optional<User> findByUser(@Param("email") String email);
}
