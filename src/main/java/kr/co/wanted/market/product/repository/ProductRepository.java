package kr.co.wanted.market.product.repository;

import kr.co.wanted.market.product.entity.Product;
import kr.co.wanted.market.trade.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p
              FROM Product p JOIN FETCH p.seller
             WHERE p.id = :id
            """)
    Optional<Product> findByIdWithSeller(@Param("id") Long id);


    /**
     * 차감 후 재고가 0개라면 상품의 상태를 '예약중' 으로 변경하면서
     * 재고가 0개 이하로 변경되지 않도록 한다.
     *
     * @param id       상품 ID
     * @param quantity 차감할 재고 수
     * @return 변경된 row 수
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("""
            UPDATE Product p
               SET p.quantity = p.quantity - :quantity,
                   p.state = CASE WHEN p.quantity - :quantity = 0 THEN 'BOOKING'
                                  ELSE p.state
                              END
             WHERE p.id = :id
               AND p.quantity - :quantity >= 0
            """)
    int subtractQuantityById(@Param("id") Long id, @Param("quantity") Long quantity);


    /**
     * 재고를 늘리면서 상태를 변경한다.
     * 구매자의 거래취소({@link Trade#revertState()})이후 재고를 다시 늘려주기 위해 호출해야 한다.
     *
     * @param id       상품 ID
     * @param quantity 늘릴 재고 수
     * @return 변경된 row 수
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("""
            UPDATE Product p
               SET p.quantity = p.quantity + :quantity,
                   p.state = CASE WHEN p.quantity + :quantity > 0 THEN 'SALE'
                                  ELSE p.state
                              END
             WHERE p.id = :id
               AND p.quantity + :quantity >= 0
            """)
    int plusQuantityById(@Param("id") Long id, @Param("quantity") Long quantity);


    /**
     * 구매자의 구매 확정({@link Trade#confirm()})이후 상품의 상태를 변경하기 위한 쿼리.
     * 모든 수량에 대해 구매자가 구매확정한 경우, 완료 상태로 변경한다.
     * 호출 전 구매확정({@link Trade#confirm()})이 먼저 이루어져야 한다.
     *
     * @param id 상품 ID
     * @return 변경된 row 수
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("""
            UPDATE Product p
               SET p.state = CASE WHEN (    p.quantity = 0
                                        AND p.state = 'BOOKING'
                                        AND NOT EXISTS (SELECT 1
                                                          FROM Trade t
                                                         WHERE t.product.id = :id
                                                           AND (t.state = 'REQUEST' OR t.state = 'BOOKING')) ) THEN 'COMPLETION'
                                  ELSE p.state
                              END
             WHERE p.id = :id
            """)
    int updateStateAfterTradeConfirmation(@Param("id") Long id);
}
