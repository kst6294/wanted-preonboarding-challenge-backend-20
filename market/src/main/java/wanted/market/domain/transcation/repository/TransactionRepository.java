package wanted.market.domain.transcation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.transcation.repository.entity.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByMember(Member member);
}
