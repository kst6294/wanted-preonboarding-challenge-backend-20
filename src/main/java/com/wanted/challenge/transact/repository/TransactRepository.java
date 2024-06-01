package com.wanted.challenge.transact.repository;

import com.wanted.challenge.transact.entity.Transact;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactRepository extends JpaRepository<Transact, Long>, TransactRepositoryCustom {

    List<Transact> findByBuyerId(Long buyerId);

    List<Transact> findByProductId(Long productId);

    Optional<Transact> findByBuyerIdAndProductId(Long buyerId, Long productId);
}
