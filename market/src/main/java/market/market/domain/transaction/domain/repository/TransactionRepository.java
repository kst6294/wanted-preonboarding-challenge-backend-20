package market.market.domain.transaction.domain.repository;

import market.market.domain.product.domain.Product;
import market.market.domain.transaction.domain.Transaction;
import market.market.domain.transaction.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findTransactionById(Long id);
    Optional<Transaction> findTransactionByProduct(Product product);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Transaction t WHERE t.buyer_id = :buyerId AND t.product = :product")
    boolean existsByBuyerIdAndProduct(@Param("buyerId") Long buyerId, @Param("product") Product product);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Transaction t WHERE t.status = :status1 OR t.status = :status2")
    boolean existsByStatusIn(@Param("status1") TransactionStatus status1, @Param("status2") TransactionStatus status2);

}
