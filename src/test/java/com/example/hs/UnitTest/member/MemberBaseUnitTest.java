package com.example.hs.UnitTest.member;

import com.example.hs.domain.auth.dto.MemberSignUpRequest;
import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.auth.service.MemberService;
import com.example.hs.domain.auth.type.Authority;
import com.example.hs.global.mail.service.MailSendService;
import com.example.hs.global.token.provider.TokenProvider;
import com.example.hs.global.token.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public abstract class MemberBaseUnitTest {
  @Mock
  protected  MemberRepository memberRepository;

  @Mock
  protected  PasswordEncoder passwordEncoder;

  @Mock
  protected  MailSendService mailSendService;

  @Mock
  protected  TokenProvider tokenProvider;

  @Mock
  protected  TokenRepository tokenRepository;

  @Mock
  protected  AuthenticationManager authenticationManager;

  @InjectMocks
  protected  MemberService memberService;

  protected  MemberSignUpRequest signUpRequest;
  protected  Member mockMember;

  @BeforeEach
  public void setUp() {
    signUpRequest = new MemberSignUpRequest(
        "testuser", "password",
        "password", "Test User");


    mockMember = Member.builder()
        .loginId("testuser")
        .password("encodedPassword")
        .name("Test User")
        .authority(Authority.ROLE_NOT_YET_MEMBER)
        .build();
  }
}
