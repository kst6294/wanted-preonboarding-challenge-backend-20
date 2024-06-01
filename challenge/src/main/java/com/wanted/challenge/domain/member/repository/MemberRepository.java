package com.wanted.challenge.domain.member.repository;

import com.wanted.challenge.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일 찾기
    boolean existsByEmail(String email);

    // 로그인
    @Query("select m from Member m where m.email = :email")
    Optional<Member> findByMember(@Param("email") String email);

}
