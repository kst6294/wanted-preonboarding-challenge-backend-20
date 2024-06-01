package com.chaewon.wanted.domain.member.controller;

import com.chaewon.wanted.base.jwt.TokenDto;
import com.chaewon.wanted.base.jwt.TokenProvider;
import com.chaewon.wanted.common.ResponseDto;
import com.chaewon.wanted.domain.member.dto.SignUpDto;
import com.chaewon.wanted.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody SignUpDto signUpDto) {
        memberService.signup(signUpDto);
        return ResponseDto.of(HttpStatus.OK, "회원가입 성공했습니다.");
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(HttpServletRequest request) {
        String refreshToken = memberService.resolveRefreshToken(request.getHeader("Authorization"));
        TokenDto tokenDto = tokenProvider.reissueAccessToken(refreshToken);

        return ResponseEntity.ok(tokenDto);
    }

}
