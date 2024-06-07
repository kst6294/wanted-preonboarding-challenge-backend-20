package com.want.BEProject.user.repository;

import com.want.BEProject.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long > {

    Optional<Users> findByUserId(String userId);
}
