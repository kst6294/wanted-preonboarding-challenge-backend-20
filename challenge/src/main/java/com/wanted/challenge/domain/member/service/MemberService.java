package com.wanted.challenge.domain.member.service;

import com.wanted.challenge.domain.member.dto.request.EmailRequestDTO;
import com.wanted.challenge.domain.member.dto.request.LoginRequestDTO;
import com.wanted.challenge.domain.member.dto.request.SignUpRequestDTO;
import com.wanted.challenge.domain.member.dto.response.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {

    // 이메일 중복확인
    boolean emailCheck(EmailRequestDTO emailRequestDTO);

    // 회원가입
    void signUp(SignUpRequestDTO signUpRequestDTO);

    // 로그인
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response);
}
