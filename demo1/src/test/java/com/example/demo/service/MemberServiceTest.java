package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;



    @Test
    void 회원가입_아이디중복() {
        //given
        //1. 회원가입 절차
        Member member = new Member("test1", "test1");
        Member member1 = memberRepository.save(member);

        //when
        Member duplMember = new Member("test1", "test2");
        assertThrows(RuntimeException.class, () -> {
            memberRepository.save(duplMember);
        });

        //then

    }


    
}