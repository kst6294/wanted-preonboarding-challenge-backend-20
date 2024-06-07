package wanted.Market.domain.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.Market.domain.product.entity.ProductStatus;
import wanted.Market.domain.transaction.entity.Transaction;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    //구매자 이름으로 현재 예약 중인 거래 내역 검색
    Optional<List<Transaction>> findByBuyerNameAndStatus(String buyerName, boolean status);

    //판매자 이름으로 현재 예약 중인 거래 내역 검색
    Optional<List<Transaction>> findByProductSellerIdentifierAndStatus(String sellerName, boolean status);

    //상세 제품 조회에 대한 거래 내역 검색
    Optional<List<Transaction>> findByProductId(long productId);
}
