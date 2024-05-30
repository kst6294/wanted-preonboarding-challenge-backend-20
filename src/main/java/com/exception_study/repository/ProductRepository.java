package com.exception_study.repository;

import com.exception_study.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("select p from Product p where p.status = '완료' and p.buyer = :userName")
    List<Product> findAllByBuyer(@Param("userName") String userName);

    @Query("select p from Product p where p.status = '예약중' and (p.seller = :userName or p.buyer = :userName)")
    List<Product> findReservedProducts(@Param("userName") String userName);

    @Query("select p from Product p where p.status = '완료' and (p.seller = :seller and p.buyer = :buyer)")
    List<Product> findTransaction_History(@Param("seller") String seller, @Param("buyer") String buyer);
}
