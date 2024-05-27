package com.chaewon.wanted.domain.member.repository;

import com.chaewon.wanted.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
