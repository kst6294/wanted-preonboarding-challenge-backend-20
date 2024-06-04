package com.example.hs.domain.auth.repository;

import com.example.hs.domain.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  boolean existsByLoginId(String loginId);
  Member findByLoginId(String loginId);
}
