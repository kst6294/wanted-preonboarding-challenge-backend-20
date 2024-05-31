package com.wanted.challenge.transact.repository;

import com.wanted.challenge.transact.entity.Transact;
import com.wanted.challenge.transact.entity.TransactLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactLogRepository extends JpaRepository<TransactLog, Long> {

    List<TransactLog> findAllByTransactIn(List<Transact> transacts);
}
