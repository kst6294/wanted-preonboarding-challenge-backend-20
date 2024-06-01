package com.chaewon.wanted.domain.member.service;

import com.chaewon.wanted.domain.member.dto.SignUpDto;

public interface MemberService {
    void signup(SignUpDto signUpDto);
    String resolveRefreshToken(String authorization);
}
