package wanted.market.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.market.domain.member.repository.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);
}
