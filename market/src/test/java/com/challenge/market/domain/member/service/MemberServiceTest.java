package com.challenge.market.domain.member.service;

import com.challenge.market.domain.member.constants.MemberErrorResult;
import com.challenge.market.domain.member.entity.Member;
import com.challenge.market.domain.member.exception.MemberNotFoundException;
import com.challenge.market.domain.member.repository.MemberRepository;
import com.challenge.market.domain.member.service.MemberService;
import com.challenge.market.web.member.dto.SignInRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    /**
     * 회원 가입 성공 테스트
     *
     * @throws Exception
     */
    @Test
    @DisplayName("회원 가입 성공")
    void testMemberSignUp() throws Exception {
        Member member = singUpRequest();

        // given
        doReturn(member).when(memberRepository).save(any(Member.class));

        // when
        Member signUpMember = memberService.signUp(member);

        // then
        assertThat(signUpMember.getId()).isEqualTo(member.getId());
        assertThat(signUpMember.getName()).isEqualTo(member.getName());

        verify(memberRepository, times(1)).save(any(Member.class));

    }



    @Test
    @DisplayName("회원 로그인 검증 실패. ID 또는 패스워드 불일치")
    void testSignIn() throws Exception {
        //given
        // respository에서 회원을 조회해 온다.

        final String name = "회원1";
        final String pw = "1234";

        Member member = Member.builder().name(name).pw(pw).build();

        doReturn(Optional.of(Member.builder().build()))
                .when(memberRepository)
                .findByName("회원1");

        assertThatThrownBy(()-> memberService.signIn(member))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage(MemberErrorResult.MEMBER_NOT_FOUND.getMessage()+member.getName());

    }

    @Test
    @DisplayName("회원 로그인 성공")
    void testSignInSuccess() throws Exception {
        // given
        String name ="chan";
        String pw = "123";

        // memberRepository.findByName() 의 return 되는 Optional<Member>
        doReturn(Optional.of(Member.builder().name(name).pw(pw).build()))
                .when(memberRepository)
                .findByName(name);

        // when
        Member loginMember = memberService.signIn(Member.builder()
                .pw(pw)
                .name(name)
                .build());

        // then
        assertThat(loginMember.getName()).isEqualTo(name);
        assertThat(loginMember.getPw()).isEqualTo(pw);

    }


    private Member singUpRequest(){
        return Member.builder().name("회원1").build();
    }
    private SignInRequest signInRequest(){
        return SignInRequest.builder().name("회원1").pw("1234").build();
    }



}
