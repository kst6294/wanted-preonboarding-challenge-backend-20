package com.wanted.challenge.account.repository;

import com.wanted.challenge.account.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByLoginId(String loginId);
}
