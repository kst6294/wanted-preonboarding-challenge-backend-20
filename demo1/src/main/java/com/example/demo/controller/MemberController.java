package com.example.demo.controller;

import com.example.demo.dto.request.MemberJoin;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*
     * 기능 : 회원가입
     * MemberController
     */
    @PostMapping("/api/v1/member/save")
    public ResponseEntity<?> memberJoin(@RequestBody MemberJoin memberJoin){

        if(memberService.memberJoin(memberJoin)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


}
