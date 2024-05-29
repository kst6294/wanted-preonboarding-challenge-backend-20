package com.wanted.market.domain.member.repository;

import com.wanted.market.domain.member.entity.Member;
import com.wanted.market.global.common.code.BaseStatusCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberIdAndStatus(String memberId, BaseStatusCode status);

    Optional<Member> findByMemberNoAndStatus(long memberNo, BaseStatusCode status);

    List<Member> findAllByMemberIdAndStatus(String memberId, BaseStatusCode status);
}

