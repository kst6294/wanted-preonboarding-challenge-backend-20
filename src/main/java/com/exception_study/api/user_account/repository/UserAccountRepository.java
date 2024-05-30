package com.exception_study.api.user_account.repository;

import com.exception_study.api.user_account.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount,String> {
}
