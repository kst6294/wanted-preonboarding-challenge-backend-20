package com.challenge.market.web.member.controller;

import com.challenge.market.domain.member.constants.MemberErrorResult;
import com.challenge.market.domain.member.entity.Member;
import com.challenge.market.domain.member.exception.MemberNotFoundException;
import com.challenge.market.web.SessionManager;
import com.challenge.market.web.member.dto.SignInRequest;
import com.challenge.market.web.member.dto.SignUpRequest;
import com.challenge.market.web.member.dto.SignUpResponse;
import com.challenge.market.domain.member.service.MemberService;
import com.challenge.market.web.member.handler.MemberExceptionHandler;
import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    @InjectMocks
    MemberController memberController;
    @Mock
    MemberService memberService;
    MockMvc mockMvc;
    Gson gson;


/*
    @Test
    @DisplayName("멤버 컨트롤러가 null이 아님")
    void testMemberControllerIsNotNull() throws Exception {
        // then
        assertThat(memberController).isNotNull();
    }
*/
    @BeforeEach()
    public void init(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .setControllerAdvice(new MemberExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("회원 가입 실패. 바디 요청 데이터가 없음")
    void testSignUpFail() throws Exception {
        // given
        String url = "/api/v1/members";

        // when
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions.andExpect(status().isBadRequest()); // status 이용해 실제 controller의 호출 결과를 테스트 할 수 있음.

    }





    @Test
    @DisplayName("회원 로그인 검증 실패. ID 또는 패스워드 불일치")
    void testSignIn() throws Exception {
        // given
        Member member = Member.from(signInRequest());

        // when
        doThrow(new MemberNotFoundException(MemberErrorResult.MEMBER_NOT_FOUND, member.getName()))
                .when(memberService)
                .signIn(any(Member.class));

        //
        assertThatThrownBy(() -> memberService.signIn(member))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage(MemberErrorResult.MEMBER_NOT_FOUND.getMessage() + member.getName());
    }

    @Test
    @DisplayName("회원 로그인 실패. 회원 서비스에서 예외 throw")
    void testMemberSignInFail() throws Exception {
        // given
        String url = "/api/v1/members/auth";
                SignInRequest request = SignInRequest.builder()
                .name("chan") // 회원 ID
                .pw("123")
                .build();

        doThrow(new MemberNotFoundException(MemberErrorResult.MEMBER_NOT_FOUND, request.getName()))
                .when(memberService)
                .signIn(any(Member.class));

        // when
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        );

        // then
        // 결과
        actions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("세션 등록 성공 테스트")
    void testLoginSessionRegisterSuccess() throws Exception {
        // given
        SessionManager sessionManager = new SessionManager();

        // 세션 생성`
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = Member.builder().name("name").build();
        sessionManager.createSession(member,response);

        // 요청에 응답쿠키 저장(클라이언트 -> 서버)
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // when
        Object result = sessionManager.getSession(request);
        sessionManager.expire(request);

        // then
        assertThat(member).isEqualTo(result);
    }

    private SignInRequest signInRequest(){
        return SignInRequest.builder().name("회원1").pw("1234").build();
    }
    private SignUpResponse signUpResponse(){
        return SignUpResponse.builder().name("회원1").id(1L).build();
    }

    private SignUpRequest signUpRequest(){
        return SignUpRequest.builder().name("회원1").pw("123").build();

    }

}
