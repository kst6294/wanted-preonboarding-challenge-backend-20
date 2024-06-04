package com.example.hs.domain.auth.controller;

import com.example.hs.domain.auth.dto.LogoutResponse;
import com.example.hs.domain.auth.dto.MemberDto;
import com.example.hs.domain.auth.dto.MemberSignInRequest;
import com.example.hs.domain.auth.dto.MemberSignUpRequest;
import com.example.hs.domain.auth.dto.ReIssueRequest;
import com.example.hs.domain.auth.service.MemberService;
import com.example.hs.global.token.dto.TokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  private final MemberService memberService;

  @PostMapping("/sign-up")
  public ResponseEntity<MemberDto> signUp(@Valid @RequestBody MemberSignUpRequest singUpRequest) {
    return ResponseEntity.ok(memberService.signUp(singUpRequest));
  }

  @PostMapping("/sign-in")
  public ResponseEntity<TokenDto> signIn(@Valid @RequestBody MemberSignInRequest signInRequest) {
    return ResponseEntity.ok(memberService.signIn(signInRequest.getUsername(), signInRequest.getPassword()));
  }

  @PostMapping("/logout")
  public ResponseEntity<LogoutResponse> logOut(@RequestHeader("Authorization") String accessToken) {
    return ResponseEntity.ok(memberService.logout(accessToken));
  }

  @PostMapping("/re-issue")
  public ResponseEntity<TokenDto> reIssue(@RequestHeader("Authorization") String refreshToken, @Valid @RequestBody ReIssueRequest request) {
    return ResponseEntity.ok(memberService.reIssue(request.getOldAccessToken(), refreshToken));
  }
}
