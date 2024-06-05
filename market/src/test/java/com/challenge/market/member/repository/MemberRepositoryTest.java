package com.challenge.market.member.repository;

import com.challenge.market.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
/*    @Test
    @DisplayName("멤버 레파지토리는 NULL이 아님")
    void MemberRepoIsNotNull() throws Exception {
        // given
        assertThat(memberRepository).isNotNull();

        // then

    }*/

    @Test
    @DisplayName("회원 저장")
    void testSaveMember() throws Exception {
        // given
        Member member = Member.builder().name("회원1").build();

        // when
        Member saveMember = memberRepository.save(member);

        // then
        assertThat(saveMember.getId()).isEqualTo(member.getId());
        assertThat(saveMember.getName()).isEqualTo(member.getName());

    }

    @Test
    @DisplayName("회원 조회")
    void testMemberFind() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);
        // when
        Member foundMember = memberRepository.findById(member.getId()).orElseGet(null);

        // then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getId()).isEqualTo(member.getId());
        assertThat(foundMember.getName()).isNotNull();

    }

    private Member createMember(){
        return Member.builder().name("회원1").build();
    }


}
