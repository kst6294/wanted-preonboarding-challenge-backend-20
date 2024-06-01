package com.wanted.challenge.account.service;

import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.model.AccountDetail;
import com.wanted.challenge.account.repository.AccountRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Account account = accountRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("다시 시도해 주세요."));
        return new AccountDetail(account.getId(), account.getLoginId(), account.getPassword(), Set.of());
    }
}
