package com.example.wanted.repository;

import com.example.wanted.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Modifying
    @Query(value = "insert into transaction(seller,buyer) values(?1,?2)", nativeQuery = true)
    public void OrderInsert(Long u_id, Long id);

//    @Modifying
//    @Query(value = "select * from transaction where  id=?1", nativeQuery = true)
//    public List<Transaction> findByProductId(Long id);

    @Modifying
    @Query(value = "select * from Transaction t join Product p"
            + " where p.p_id = t.product_id and t.user_id=:id",
            nativeQuery = true)
    public List<Transaction> findByBuyId(@Param("id") Long id);

    @Modifying
    @Query(value = "select * from Transaction t join Product p"
            + " where p.p_id = t.product_id and p.u_id=:id",
            nativeQuery = true)
    public List<Transaction> findBySellId(@Param("id") Long id);

//    @Modifying
//    @Query(value = "select * from Transaction t join Product p"
//            + " where p.u_id:=id and t.t_id=:id or t.user_id=:id", nativeQuery = true)
//    public List<Transaction> findAllByUserId(@Param("id") Long id);

    @Query(value = "select user_id from Transaction"
            + " where product_id=:id", nativeQuery = true)
    public Long findByBuyerId(@Param("id") Long id);
}
