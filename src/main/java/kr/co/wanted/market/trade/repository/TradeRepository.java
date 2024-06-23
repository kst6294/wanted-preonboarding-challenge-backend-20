package kr.co.wanted.market.trade.repository;

import kr.co.wanted.market.member.entity.Member;
import kr.co.wanted.market.product.entity.Product;
import kr.co.wanted.market.trade.entity.Trade;
import kr.co.wanted.market.trade.enums.TradeState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {


    @Query("""
            SELECT t
              FROM Trade t JOIN FETCH t.product JOIN FETCH t.seller
             WHERE t.seller.id = :sellerId
            """)
    Page<Trade> findAllBySellerIdWithProductAndBuyer(@Param("sellerId") Long sellerId, Pageable pageable);


    @Query("""
            SELECT t
              FROM Trade t JOIN FETCH t.product JOIN FETCH t.seller
             WHERE t.buyer.id = :buyerId
            """)
    Page<Trade> findAllByBuyerIdWithProductAndBuyer(@Param("buyerId") Long buyerId, Pageable pageable);


    boolean existsByBuyerAndProductAndStateIsNot(Member seller, Product product, TradeState state);


    @Query("""
            SELECT t
              FROM Trade t JOIN FETCH t.buyer JOIN FETCH t.seller JOIN FETCH t.product
             WHERE t.id = :id
            """)
    Optional<Trade> findByIdWithBuyerAndSellerAndProduct(@Param("id") Long id);


    @Query("""
            SELECT t
              FROM Trade t JOIN FETCH t.buyer JOIN FETCH t.seller JOIN FETCH t.product
             WHERE t.seller.id = :sellerId
               AND t.product.id = :productId
            """)
    List<Trade> findAllBySellerIdAndProductId(@Param("sellerId") Long sellerId, @Param("productId") Long productId);


    @Query("""
            SELECT t
              FROM Trade t JOIN FETCH t.buyer JOIN FETCH t.seller JOIN FETCH t.product
             WHERE t.buyer.id = :buyerId
               AND t.product.id = :productId
            """)
    List<Trade> findAllByBuyerIdAndProductId(@Param("buyerId") Long buyerId, @Param("productId") Long productId);

}
