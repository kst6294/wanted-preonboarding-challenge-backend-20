package com.example.wantedmarketapi.repository;

import com.example.wantedmarketapi.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {}

