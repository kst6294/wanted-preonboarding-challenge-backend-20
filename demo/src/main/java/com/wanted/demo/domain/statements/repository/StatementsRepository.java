package com.wanted.demo.domain.statements.repository;

import com.wanted.demo.domain.product.entity.Product;
import com.wanted.demo.domain.statements.dto.response.StatementsHistoryResponseDTO;
import com.wanted.demo.domain.statements.entity.Statements;
import com.wanted.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

}
