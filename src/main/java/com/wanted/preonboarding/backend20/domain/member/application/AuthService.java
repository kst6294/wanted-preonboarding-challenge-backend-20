package com.wanted.preonboarding.backend20.domain.member.application;

import com.wanted.preonboarding.backend20.domain.member.dto.SignInDto;
import com.wanted.preonboarding.backend20.domain.member.dto.SignUpDto;

public interface AuthService {
    void signUp(SignUpDto dto);
    String signIn(SignInDto dto);
}
