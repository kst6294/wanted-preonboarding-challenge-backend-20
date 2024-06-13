package io.taylor.wantedpreonboardingchallengebackend20.member.controller;

import io.taylor.wantedpreonboardingchallengebackend20.member.entity.Member;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.JoinReqeust;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.LoginReqeust;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.response.MemberLoginResponse;
import io.taylor.wantedpreonboardingchallengebackend20.member.service.MemberService;
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
    public ResponseEntity<Object> join(@RequestBody JoinReqeust request) {
        Object response = memberService.joinMember(new Member(request));
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReqeust request) {
        MemberLoginResponse response = memberService.loginMember(new Member(request.getEmail(), request.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader HttpHeaders header, String str) {
        Object response = memberService.logoutMember(header, str);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
