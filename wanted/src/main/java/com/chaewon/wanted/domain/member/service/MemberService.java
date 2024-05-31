package com.chaewon.wanted.domain.member.service;

import com.chaewon.wanted.base.jwt.TokenDto;
import com.chaewon.wanted.domain.member.dto.SignInDto;
import com.chaewon.wanted.domain.member.dto.SignUpDto;

public interface MemberService {
    void signup(SignUpDto signUpDto);
    TokenDto signIn(SignInDto signInDto);
    String resolveRefreshToken(String authorization);
}
