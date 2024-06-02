package com.wanted.wantedpreonboardingchallengebackend20.domain.user.repository;

import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>,UserRepositoryCustom {
}
