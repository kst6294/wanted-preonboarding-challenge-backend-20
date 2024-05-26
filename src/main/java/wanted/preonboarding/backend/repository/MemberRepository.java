package wanted.preonboarding.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.preonboarding.backend.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
