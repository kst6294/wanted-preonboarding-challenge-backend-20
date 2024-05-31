package com.wanted.challenge.security;

import com.wanted.challenge.account.model.AccountDetail;
import java.util.List;
import java.util.Set;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithAccountSecurityContextFactory
        implements WithSecurityContextFactory<WithAccount> {

    @Override
    public SecurityContext createSecurityContext(WithAccount annotation) {

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        AccountDetail accountDetail =
                new AccountDetail(1L, annotation.username(), annotation.password(), Set.of());

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(accountDetail, annotation.password(), List.of());

        securityContext.setAuthentication(authenticationToken);

        return securityContext;
    }
}
