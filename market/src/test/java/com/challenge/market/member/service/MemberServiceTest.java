package com.challenge.market.member.service;

import com.challenge.market.member.domain.Member;
import com.challenge.market.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입 성공")
    void testMemberJoin() throws Exception {

        // given
        Member member = createMember();
        doReturn(createMember()).when(memberRepository).save(any(Member.class));
        // when
        Member signUpMember = memberService.signUp(member);
        // then
        assertThat(signUpMember.getId()).isEqualTo(member.getId());
        assertThat(signUpMember.getName()).isEqualTo(member.getName());

        verify(memberRepository, times(1)).save(any(Member.class));

    }

    private Member createMember(){
        return Member.builder().name("회원1").build();
    }



}
