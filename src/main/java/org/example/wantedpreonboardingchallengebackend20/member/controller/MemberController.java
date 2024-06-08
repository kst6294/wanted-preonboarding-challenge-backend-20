package org.example.wantedpreonboardingchallengebackend20.member.controller;

import org.example.wantedpreonboardingchallengebackend20.common.model.CommonResponse;
import org.example.wantedpreonboardingchallengebackend20.member.entity.Member;
import org.example.wantedpreonboardingchallengebackend20.member.model.request.MemberReqeust;
import org.example.wantedpreonboardingchallengebackend20.member.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<CommonResponse<Object>> joinMember(MemberReqeust memberRequest) {
        CommonResponse<Object> response;
        if (memberRequest.password().equals(memberRequest.passwordCheck())) {
            response = memberService.joinMember(new Member(memberRequest));
        } else {
            response = CommonResponse.builder().result(true).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Object>> loginMember(MemberReqeust memberRequest) {
        CommonResponse<Object> response = memberService.loginMember(new Member(memberRequest.email(), memberRequest.password()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Object>> logoutMember(@RequestHeader HttpHeaders header, String str) {
        CommonResponse<Object> response = memberService.logoutMember(header, str);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
