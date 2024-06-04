package com.wanted.demo.domain.statements.repository;

import com.wanted.demo.domain.product.entity.Product;
import com.wanted.demo.domain.statements.dto.response.BuyerReservationResponseDTO;
import com.wanted.demo.domain.statements.dto.response.MyProductStatementsResponseDTO;
import com.wanted.demo.domain.statements.dto.response.SellerReservationResponseDTO;
import com.wanted.demo.domain.statements.dto.response.StatementsHistoryResponseDTO;
import com.wanted.demo.domain.statements.entity.Statements;
import com.wanted.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface StatementsRepository extends JpaRepository<Statements, Long> {


    //판매자에게 필요한
    @Query("select new com.wanted.demo.domain.statements.dto.response.StatementsHistoryResponseDTO" + "(s.id, s.price, s.purchaseStatus, s.purchaseStatus)"
            + "from Statements s join s.user u where s.product = :product")
    List<StatementsHistoryResponseDTO> findStatementHistoriesListByProduct(@Param("product")Product product);

    //유저와 상품
    @Query("select new com.wanted.demo.domain.statements.dto.response.StatementsHistoryResponseDTO" + "(s.id, s.price, s.purchaseStatus, s.purchaseStatus)"
            + "from Statements s join s.user u where s.product = :product and s.user = :user")
    List<StatementsHistoryResponseDTO> findStatementHistoriesList(@Param("user")User user, @Param("product")Product product);

    //구매내역이 존재하는지
    Optional<Statements> findByUserAndProduct(User user, Product product);

    //내가 구매한 용품
    @Query("select new com.wanted.demo.domain.statements.dto.response.MyProductStatementsResponseDTO"+"(s.id, s.price, p.name, p.createdDateTime)"
            + "from Statements s join s.product p where s.user = :user and s.purchaseStatus = true and s.sellStatus = true")
    List<MyProductStatementsResponseDTO> findStatementsByUser(@Param("user")User user);

    //유저가 예약한 용품(구매자) -> 판매용품 정보
    @Query("select new com.wanted.demo.domain.statements.dto.response.BuyerReservationResponseDTO"+"(s.id, p.id, p.name, s.price, p.createdDateTime, s.purchaseStatus, s.sellStatus)"
            + "from Statements s join s.product p where s.user = :user and p.state = 'RESERVATION'")
    List<BuyerReservationResponseDTO> findStatementsByBuyer(@Param("user")User user);

    //유저가 예약한 용품(판매자) -> 구매자 정보
    @Query("select new com.wanted.demo.domain.statements.dto.response.SellerReservationResponseDTO"+"(s.id, u.email, p.id, p.name, s.price, p.createdDateTime, s.purchaseStatus, s.sellStatus)"
    + "from Statements s join s.product p join s.user u where p.user = :user and p.state = 'RESERVATION' and p = s.product")
    List<SellerReservationResponseDTO> findStatementsBySeller(@Param("user")User user);


    //판매 승인(패치조인으로 statements와 product 상태 변경 or entityGraph)
    //유저는 1(판매자)
    @Query("select s from Statements s join fetch s.product join fetch s.product.user where s.id = :id")
    Optional<Statements> findByStatements(@Param("id") Long id); //4번내역
}
