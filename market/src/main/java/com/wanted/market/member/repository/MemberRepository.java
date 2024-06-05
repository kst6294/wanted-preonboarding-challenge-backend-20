package com.wanted.market.member.repository;

import com.wanted.market.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Boolean existsByEmail(String email);

    Member findByEmail(String email);
}
