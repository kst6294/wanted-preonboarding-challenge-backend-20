package com.challenge.market.member.controller;

import com.challenge.market.member.dto.SignUpRequest;
import com.challenge.market.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    @InjectMocks MemberController memberController;
    @Mock
    MemberService memberService;


    MockMvc mockMvc;

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
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    @DisplayName("회원 가입 실패")
    void testSignUpFail() throws Exception {

        // given
        // 회원 등록을 위해 member 객체가 넘어온다.
        // member객체의 특정 값이 포함되어 있지 않을 경우 오류 처리한다.
        String url = "/members/";
        SignUpRequest memberSignUpRequest = new SignUpRequest();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(url));

        // then
        actions.andExpect(status().isBadRequest());
        

    }
    @Test
    @DisplayName("회원가입 요청 성공")
    void testSignUpSuccess() throws Exception {
        // given

        // when
        // then

    }


}
