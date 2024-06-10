package org.example.wantedpreonboardingchallengebackend20.member.controller;

import org.example.wantedpreonboardingchallengebackend20.common.model.CommonResponse;
import org.example.wantedpreonboardingchallengebackend20.member.entity.Member;
import org.example.wantedpreonboardingchallengebackend20.member.model.request.JoinReqeust;
import org.example.wantedpreonboardingchallengebackend20.member.model.request.LoginReqeust;
import org.example.wantedpreonboardingchallengebackend20.member.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<CommonResponse<Object>> joinMember(@RequestBody JoinReqeust request) {
        CommonResponse<Object> response;
        response = memberService.joinMember(new Member(request));
        if (response != null) {
            response = CommonResponse.builder().result(true).build();
        } else {
            response = CommonResponse.builder().result(false).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Object>> loginMember(@RequestBody LoginReqeust request) {
        CommonResponse<Object> response = memberService.loginMember(new Member(request.getEmail(), request.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Object>> logoutMember(@RequestHeader HttpHeaders header, String str) {
        CommonResponse<Object> response = memberService.logoutMember(header, str);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
