package com.example.hs.UnitTest.member;


import static com.example.hs.global.exception.ErrorCode.ALREADY_EXISTS_LOGIN_ID;
import static com.example.hs.global.exception.ErrorCode.NOT_EQUAL_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.hs.domain.auth.dto.MemberDto;
import com.example.hs.domain.auth.dto.MemberSignUpRequest;
import com.example.hs.domain.auth.entity.Member;
import com.example.hs.global.exception.CustomException;
import jakarta.mail.MessagingException;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MemberSignUpUnitTest extends MemberBaseUnitTest{

  @DisplayName("회원가입 성공")
  @Test
  public void testSignUp() throws MessagingException, NoSuchAlgorithmException {
    when(memberRepository.existsByLoginId(signUpRequest.getUsername())).thenReturn(false);
    when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");
    when(memberRepository.save(any(Member.class))).thenReturn(mockMember);

    doNothing().when(mailSendService).emailAuth(signUpRequest.getUsername());

    MemberDto memberDto = memberService.signUp(signUpRequest);

    assertNotNull(memberDto);
    assertEquals("이메일 인증 후 로그인이 가능합니다.", memberDto.getMessage());
    verify(memberRepository, times(1)).save(any(Member.class));
  }

  @DisplayName("회원가입 실패1: 같은 loginId로 다른 회원이 존재할 경우")
  @Test
  public void testSignUp_fail1() throws MessagingException, NoSuchAlgorithmException {
    when(memberRepository.existsByLoginId(signUpRequest.getUsername())).thenReturn(true);

    CustomException exception = assertThrows(CustomException.class, () -> {
      memberService.signUp(signUpRequest);
    });

    assertEquals(ALREADY_EXISTS_LOGIN_ID, exception.getErrorCode());
    verify(memberRepository, times(1)).existsByLoginId(signUpRequest.getUsername());
  }

  @DisplayName("회원가입 실패2: 비밀번호가 일치하지 않는 경우")
  @Test
  public void testSignUp_fail2() throws MessagingException, NoSuchAlgorithmException {

    MemberSignUpRequest wrongSignUpRequest = new MemberSignUpRequest(
        "testuser", "password",
        "differentPassword", "Test User");

    CustomException exception = assertThrows(CustomException.class, () -> {
      memberService.signUp(wrongSignUpRequest);
    });

    assertEquals(NOT_EQUAL_PASSWORD, exception.getErrorCode());
  }
}
