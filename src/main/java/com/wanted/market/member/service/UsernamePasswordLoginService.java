package com.wanted.market.member.service;

import com.wanted.market.common.authentication.model.LoginMember;
import com.wanted.market.common.authentication.model.SessionLoginMember;
import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.member.domain.Member;
import com.wanted.market.member.domain.MemberRepository;
import com.wanted.market.member.domain.PasswordEncoder;
import com.wanted.market.member.domain.vo.MemberInfo;
import com.wanted.market.member.exception.AuthenticationException;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UsernamePasswordLoginService implements LoginService<UsernamePasswordLoginService.UsernamePasswordLoginCommand> {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsernamePasswordLoginService(MemberRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean supports(Class<? extends LoginCommand> clazz) {
        return clazz.equals(UsernamePasswordLoginCommand.class);
    }

    @Override
    @Timed("UsernamePasswordLoginService.login")
    public LoginMember login(UsernamePasswordLoginCommand cmd) {
        logger.info("try username-password login: {}", cmd.username());
        String username = cmd.username();
        String password = cmd.password();
        MemberInfo info;
        try {
            Member findMember = repository.findByUsernameOrThrow(username);
            info = findMember.login(password, passwordEncoder);
            if (info == null) {
                logger.info("login failed: password mismatch");
                throw new AuthenticationException("bad credential");
            }
        } catch (NotFoundException e) {
            logger.info("login failed: no such member");
            throw new AuthenticationException("no such user", e);
        }
        return SessionLoginMember.authenticated(info.getId(), info.getUsername());
    }

    public record UsernamePasswordLoginCommand(
            String username,
            String password
    ) implements LoginCommand {
    }
}
