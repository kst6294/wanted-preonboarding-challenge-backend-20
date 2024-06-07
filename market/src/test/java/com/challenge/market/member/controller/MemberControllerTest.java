package com.challenge.market.member.controller;

import com.challenge.market.member.domain.Member;
import com.challenge.market.member.dto.SignUpRequest;
import com.challenge.market.member.dto.SignUpResponse;
import com.challenge.market.member.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    @InjectMocks MemberController memberController;
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
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    // 회원 가입 실패
    @Test
    @DisplayName("회원 가입 실패")
    void testSignUpFail() throws Exception {
        // given
        // 회원 등록을 위해 member 객체가 넘어온다.
        // member객체의 특정 값이 포함되어 있지 않을 경우 오류 처리한다.

        // signup 요청이 정상적으로 들어왔는지 확인해야한다.
        // signup 요청시 HttpMessageConvertor 가 request 객체를 json 에서 object로 컨버팅 하는 동작을 구현해야함.

        // 요청테스트 시 필요한것
        // 1. url
        String url = "/members";
        // 2. http method
        // 3. 요청을 통해 넘어 온 값
        // Controller 에서 요청을 테스트 하기 위해선 가짜 요청을 만들어야한다.
        // MockMvc.perform() 이용해 컨트롤러 요청과 응답 테스트를 할 수 있음.
        // MockMvcRequestBuilder.post() 메서드를 이용해 가짜 Post 요청을 만들 수 있음.
        ResultActions actions = mockMvc.perform( // 다음 요청을 통해 컨트롤러를 호출한다.
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(signUpRequest())) // content() 메서드를 통해 컨텐트 바디의 내용을 주입 할 수 있음. 클라이언트에서 넘어오는 요청은 json 형태로 넘어올 것이므로 Gson을 이용해 테스트 값을 JSON으로 만들어야함.
                        .contentType(MediaType.APPLICATION_JSON) // 미디어 타입을 json으로 설정
        );

        actions.andExpect(status().isOk()); // status 이용해 실제 controller의 호출 결과를 테스트 할 수 있음.

    }


    private SignUpResponse signUpResponse(){
        return SignUpResponse.builder().name("회원1").id(1L).build();
    }

    private SignUpRequest signUpRequest(){
        return SignUpRequest.builder().name("회원1").pw("123").build();

    }





}
