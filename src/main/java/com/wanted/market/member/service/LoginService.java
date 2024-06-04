package com.wanted.market.member.service;

import com.wanted.market.common.authentication.model.LoginMember;

public interface LoginService<T extends LoginService.LoginCommand> {
    boolean supports(Class<? extends LoginCommand> clazz);

    LoginMember login(T cmd);

    interface LoginCommand {
    }
}
