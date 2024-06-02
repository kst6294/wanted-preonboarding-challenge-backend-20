package com.backend.market.Repository;

import com.backend.market.DAO.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByLogInId(String id);

    Optional<Member> findByName(String name);
}
