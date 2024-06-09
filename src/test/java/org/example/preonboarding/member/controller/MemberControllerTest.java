package org.example.preonboarding.member.controller;

import org.example.preonboarding.ControllerTestSupport;
import org.example.preonboarding.member.model.payload.request.SignupRequest;
import org.example.preonboarding.member.model.payload.response.MemberResponse;
import org.example.preonboarding.member.model.payload.response.SignupResponse;
import org.example.preonboarding.member.model.payload.response.WithdrawResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.preonboarding.common.enums.ResultCode.WITHDRAW_SUCCESS;
import static org.example.preonboarding.member.model.enums.Role.MEMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTestSupport {

    @BeforeEach
    void setUp() {
    }

    @DisplayName("전체 회원을 조회한다. 회원 데이터가 없을 경우 data는 null을 반환한다.")
    @Test
    void getMemberListNoResultTest() throws Exception {
        // given
        List<MemberResponse> result = List.of();

        // when
        when(memberService.getUsers()).thenReturn(result);

        // then
        mockMvc.perform(get("/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

    @DisplayName("전체 회원을 조회한다. 회원 데이터가 있을 경우 data를 반환한다.")
    @Test
    void getMemberListWithResultTest() throws Exception {
        // given
        List<MemberResponse> result = createMemberResponses();

        // when
        when(memberService.getUsers()).thenReturn(result);

        // then
        mockMvc.perform(get("/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].userId").value("admin"))
                .andExpect(jsonPath("$.data[0].name").value("admin"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].userId").value("testA"))
                .andExpect(jsonPath("$.data[1].name").value("testAA"))
        ;
    }

    @DisplayName("userid 값으로 특정 회원을 조회한다.")
    @Test
    void getMemberTest() throws Exception {
        // given
        MemberResponse memberResponse = createMemberResponse();

        //when
        when(memberService.getUserByUserId(memberResponse.getUserId())).thenReturn(memberResponse);

        // then
        mockMvc.perform(get("/members/" + memberResponse.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.userId").value("admin"))
                .andExpect(jsonPath("$.data.name").value("admin"))
                .andExpect(jsonPath("$.data.name").value("admin"))

        ;
    }

    @DisplayName("user롤 생성한다.")
    @Test
    void createMemberTest() throws Exception {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
                .userId("aaaaa")
                .name("userA")
                .password("testPassword")
                .role(MEMBER)
                .build();

        SignupResponse signupResponse = SignupResponse.builder()
                .userId("aaaaa")
                .name("userA")
                .build();
        //when
        when(memberService.createUser(any(SignupRequest.class))).thenReturn(signupResponse);

        // then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signupRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("signup success"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.userId").value(signupResponse.getUserId()))
                .andExpect(jsonPath("$.data.name").value(signupResponse.getName()))
        ;
    }

    @DisplayName("userId를 기반으로 사용자를 삭제한다.")
    @Test
    void deleteMemberTest() throws Exception {
        // given
        WithdrawResponse withdrawResponse = WithdrawResponse.builder()
                .deletedCount(1)
                .build();

        String userId = "aaaaa";
        // when

        //when
        when(memberService.deleteUser(any(String.class))).thenReturn(withdrawResponse);
        // then
        mockMvc.perform(delete("/members/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.code").value(WITHDRAW_SUCCESS.code))
                .andExpect(jsonPath("$.message").value(WITHDRAW_SUCCESS.message))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
    }

    private MemberResponse createMemberResponse() {
        return MemberResponse.builder()
                .id(1L)
                .userId("admin")
                .name("admin")
                .createdAt(LocalDateTime.now())
                .build();
    }


    private List<MemberResponse> createMemberResponses() {
        return List.of(
                MemberResponse.builder()
                        .id(1L)
                        .userId("admin")
                        .name("admin")
                        .createdAt(LocalDateTime.now())
                        .build(),
                MemberResponse.builder()
                        .id(2L)
                        .userId("testA")
                        .name("testAA")
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }
}