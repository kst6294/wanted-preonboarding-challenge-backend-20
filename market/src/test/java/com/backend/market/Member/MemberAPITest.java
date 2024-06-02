package com.backend.market.Member;

import com.backend.market.DAO.Entity.Member;
import com.backend.market.Repository.MemberRepository;
import com.backend.market.Request.MemberReq;
import com.backend.market.Service.Member.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MemberAPITest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @Test
    void testSignUp()
    {
        MemberReq member = new MemberReq();
        member.setLogInId("marin1638");
        member.setName("홍길동");
        member.setPassword("12345678");
        this.memberService.doSignUp(member);

        Optional<Member> findmember = this.memberRepository.findByLogInId(member.getLogInId());

        assertEquals(findmember.get().getLogInId(),member.getLogInId());
        assertEquals(findmember.get().getName(),member.getName());
        assertEquals(findmember.get().getPassword(),member.getPassword());

    }
}
