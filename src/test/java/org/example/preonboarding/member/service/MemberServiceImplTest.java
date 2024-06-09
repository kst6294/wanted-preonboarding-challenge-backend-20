package org.example.preonboarding.member.service;

import org.example.preonboarding.IntegrationTestSupport;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.member.model.payload.request.SignupRequest;
import org.example.preonboarding.member.model.payload.response.SignupResponse;
import org.example.preonboarding.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.example.preonboarding.member.model.enums.Role.MEMBER;

class MemberServiceImplTest extends IntegrationTestSupport {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("회원을 생성한다.")
    @Test
    void test() {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
                .userId("testId")
                .name("testName")
                .password("password")
                .role(MEMBER)
                .build();

        // when
        SignupResponse signupResponse = memberService.createUser(signupRequest);


        // then
        assertThat(signupResponse)
                .extracting("userId", "name")
                .contains("testId", "testName");

        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(2)
                .extracting("userId", "name") // InitializeDataLoader에 의해 springboottest가 뜰때 admin data init.containsExactlyInAnyOrder(
                .containsExactlyInAnyOrder(
                        tuple("admin", "admin"),
                        tuple("testId", "testName")
                );

    }

}