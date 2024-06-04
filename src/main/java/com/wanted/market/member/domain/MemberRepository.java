package com.wanted.market.member.domain;

import com.wanted.market.common.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    default Member findByUsernameOrThrow(String username) throws NotFoundException {
        return findByUsername(username).orElseThrow(() -> new NotFoundException("no such member"));
    }
}
