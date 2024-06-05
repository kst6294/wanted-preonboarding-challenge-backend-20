package org.example.preonboarding.auth;

import org.example.preonboarding.IntegrationTestSupport;
import org.example.preonboarding.auth.payload.request.LoginRequest;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.example.preonboarding.member.model.enums.Role.MEMBER;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .userId("test")
                .name("testName")
                .password(passwordEncoder.encode("test"))
                .role(MEMBER)
                .build();
        Member savedMember = memberRepository.save(member);
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("로그인 성공")
    public void loginSuccess() throws Exception {
        // given
        LoginRequest loginRequest = LoginRequest.builder()
                .userId("test")
                .password("test")
                .build();

        // when then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(jsonPath("$.message").value("login success"))
                .andExpect(jsonPath("$.data.type").value("Bearer "))
                .andExpect(jsonPath("$.data.accessToken", notNullValue()))
                .andExpect(jsonPath("$.data.accessTokenExpired", notNullValue()))
        ;
    }

    @Test
    @DisplayName("암호 틀려서 로그인 실패")
    public void loginFailure() throws Exception {
        // given
        LoginRequest loginRequest = LoginRequest.builder()
                .userId("test")
                .password("wrongpassword")
                .build();

        // when then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("회원가입된 아이디가 아니라서 로그인 실패")
    public void loginFailure2() throws Exception {
        // given
        LoginRequest loginRequest = LoginRequest.builder()
                .userId("aaa")
                .password("test")
                .build();

        // when then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}