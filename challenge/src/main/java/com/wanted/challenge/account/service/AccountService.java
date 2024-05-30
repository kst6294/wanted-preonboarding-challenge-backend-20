package com.wanted.challenge.account.service;

import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(String loginId, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Account account = new Account(loginId, encodedPassword);
        accountRepository.save(account);
    }
}
