package com.backend.market.Service.Member;

import com.backend.market.DAO.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

}
