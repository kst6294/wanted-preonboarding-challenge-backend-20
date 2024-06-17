package io.taylor.wantedpreonboardingchallengebackend20.member.controller;

import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.JoinRequestDto;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.LoginRequestDto;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.response.JoinResponseDto;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.response.LoginResponseDto;
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
    public ResponseEntity<JoinResponseDto> join(@RequestBody JoinRequestDto request) {
        JoinResponseDto response = memberService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = memberService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader HttpHeaders header, String str) {
        Object response = memberService.logout(header, str);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
