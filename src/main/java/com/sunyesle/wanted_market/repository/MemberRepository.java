package com.sunyesle.wanted_market.repository;

import com.sunyesle.wanted_market.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
