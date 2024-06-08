package org.example.wantedpreonboardingchallengebackend20.member.repository;

import org.example.wantedpreonboardingchallengebackend20.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member save(Member member);
    Member findMemberByEmail(String email);
    Member findMemberByEmailAndPassword(String email, String password);
}
