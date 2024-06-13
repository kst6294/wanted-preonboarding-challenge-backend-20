package com.challenge.market.domain.member.repository;

import com.challenge.market.domain.member.entity.Member;
import com.challenge.market.domain.member.repository.MemberRepository;
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

    @Test
    @DisplayName("회원조회_이름")
    void testFindMemberByName() throws Exception {
        // given
        Member member = Member.builder().name("chan").pw("123").build();

        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findByName(member.getName()).orElseGet(null);

        // then
        assertThat(findMember.getId()).isNotNull();
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember.getPw()).isEqualTo(member.getPw());

    }

    private Member createMember(){
        return Member.builder().name("회원1").build();
    }


}
