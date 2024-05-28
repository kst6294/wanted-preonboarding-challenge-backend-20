package com.backend.market.Service.Product;

import com.backend.market.DAO.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select * from product where status = 1 and member_user_id = ?1", nativeQuery = true)
    List<Product> findAllReservationById(Long id);

    @Query(value = "select * from product where status = 0 and member_user_id = ?1", nativeQuery = true)
    List<Product> findAllCompleteById(Long id);
}
