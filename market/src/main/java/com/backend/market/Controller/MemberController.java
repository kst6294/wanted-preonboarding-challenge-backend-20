package com.backend.market.Controller;

import com.backend.market.DAO.Entity.Member;
import com.backend.market.Request.MemberReq;
import com.backend.market.Service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> SignUp(@RequestBody MemberReq memberReq)
    {

        if(this.memberService.doSignUp(memberReq).equals("회원가입 완료"))
        {
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        }
        return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
    }


}
