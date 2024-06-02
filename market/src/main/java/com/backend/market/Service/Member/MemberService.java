package com.backend.market.Service.Member;

import com.backend.market.DAO.Entity.Member;
import com.backend.market.Repository.MemberRepository;
import com.backend.market.Request.MemberReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private MemberRepository memberRepository;

    /*
    뢰원가입
     */
    public String doSignUp(MemberReq memberReq)
    {
        //ID 중복검사
        Optional<Member> checkMember = this.memberRepository.findByLogInId(memberReq.getLogInId());
        if(checkMember.isEmpty()) return "이미 등록된 ID입니다.";

        Member member = new Member();
        member.setLogInId(memberReq.getLogInId());
        member.setName(memberReq.getName());
        member.setPassword(memberReq.getPassword());
        member.setProductList(new ArrayList<>());

        if(!validation(member)) return "유효한 값이 아닙니다.";

        this.memberRepository.save(member);

        return "회원가입 완료";
    }

    private boolean validation(Member member){
        if(member.getPassword().length() > 20) return false;

        if(member.getName().isEmpty()) return false;

        return true;

    }
}
