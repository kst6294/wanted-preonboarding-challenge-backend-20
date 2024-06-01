package com.chaewon.wanted.domain.member.service;

import com.chaewon.wanted.base.jwt.TokenDto;
import com.chaewon.wanted.domain.member.dto.SignInDto;

public interface AuthService {
    TokenDto signIn(SignInDto signInDto);
}
