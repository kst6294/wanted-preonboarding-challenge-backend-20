package market.market.domain.transaction.domain.repository;

import market.market.domain.product.domain.Product;
import market.market.domain.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findTransactionById(Long id);
    Optional<Transaction> findTransactionByProduct(Product product);
}
