package com.wanted.challenge.domain.member.service.impl;

import com.wanted.challenge.domain.exception.exception.MemberException;
import com.wanted.challenge.domain.exception.info.MemberExceptionInfo;
import com.wanted.challenge.domain.member.dto.request.EmailRequestDTO;
import com.wanted.challenge.domain.member.dto.request.LoginRequestDTO;
import com.wanted.challenge.domain.member.dto.request.SignUpRequestDTO;
import com.wanted.challenge.domain.member.dto.response.LoginResponseDTO;
import com.wanted.challenge.domain.member.entity.Member;
import com.wanted.challenge.domain.member.repository.MemberRepository;
import com.wanted.challenge.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    // 이메일 중복확인
    @Override
    @Transactional(readOnly = true)
    public boolean emailCheck(EmailRequestDTO emailRequestDTO) {

        return memberRepository.existsByEmail(emailRequestDTO.getEmail());

    }

    // 회원가입
    @Override
    @Transactional
    public void signUp(SignUpRequestDTO signUpRequestDTO) {

        if (memberRepository.existsByEmail(signUpRequestDTO.getEmail())) {
            throw new MemberException(MemberExceptionInfo.EXISTS_MEMBER, "이미 존재하는 이메일입니다.");
        }

        Member member = signUpRequestDTO.toEntity(encoder.encode(signUpRequestDTO.getPassword()));

        memberRepository.save(member);
    }

    // 로그인
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response) {

        Member member = memberRepository.findByMember(loginRequestDTO.getEmail())
                .orElseThrow(() -> new MemberException(MemberExceptionInfo.NOT_EXISTS_MEMBER, loginRequestDTO.getEmail() + " 이메일로 로그인 시도(없는 이메일)"));

        if (!encoder.matches(loginRequestDTO.getPassword(), member.getPassword())) {
            throw new MemberException(MemberExceptionInfo.NOT_MATCH_PASSWORD, loginRequestDTO.getEmail() +
                    " 이메일로 로그인 시도(틀린 비밀번호 : " + loginRequestDTO.getPassword() + ")");
        }

        addSession(request, member);

        return LoginResponseDTO.toDTO(member);
    }

    // 세션 생성
    private void addSession(HttpServletRequest request, Member member) {

        HttpSession session = request.getSession();
        session.setAttribute("userId", member.getId());
        session.setMaxInactiveInterval(1800); // 30분

    }

}
