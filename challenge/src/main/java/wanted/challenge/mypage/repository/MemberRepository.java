package wanted.challenge.mypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.challenge.mypage.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
