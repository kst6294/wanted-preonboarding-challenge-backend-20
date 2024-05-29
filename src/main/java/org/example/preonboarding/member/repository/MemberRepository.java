package org.example.preonboarding.member.repository;


import org.example.preonboarding.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsById(Long id);
    Optional<Member> findByUserId(String userId);
    int deleteByUserId(String userId);
}