package io.taylor.wantedpreonboardingchallengebackend20.member.controller;

import io.taylor.wantedpreonboardingchallengebackend20.member.entity.Member;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.JoinRequest;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.LoginRequest;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.response.JoinResponse;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.response.LoginResponse;
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
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest request) {
        JoinResponse response = memberService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        LoginResponse response = memberService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader HttpHeaders header, String str) {
        Object response = memberService.logout(header, str);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
