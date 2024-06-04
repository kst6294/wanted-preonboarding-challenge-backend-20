package com.example.hs.domain.auth.controller;

import com.example.hs.domain.auth.dto.MemberDto;
import com.example.hs.domain.auth.dto.MemberSignUpRequest;
import com.example.hs.domain.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  private final MemberService memberService;

  @PostMapping("/sign-up")
  public ResponseEntity<MemberDto> signUp(@RequestBody MemberSignUpRequest singUpRequest) {
    return ResponseEntity.ok(memberService.signUp(singUpRequest));
  }
}
