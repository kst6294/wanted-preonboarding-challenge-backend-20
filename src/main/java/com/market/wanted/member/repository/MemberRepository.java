package com.market.wanted.member.repository;

import com.market.wanted.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {



}
