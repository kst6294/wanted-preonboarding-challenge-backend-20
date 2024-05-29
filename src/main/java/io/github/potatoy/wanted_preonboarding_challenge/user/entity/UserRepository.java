package io.github.potatoy.wanted_preonboarding_challenge.user.entity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email); // email로 사용자 정보 가져오기
}
