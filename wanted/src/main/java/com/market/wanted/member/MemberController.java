package com.market.wanted.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<Long> sign(@RequestBody MemberSignDto memberSignDto){
        Long memberId = memberService.sign(memberSignDto);
        return ResponseEntity.ok(memberId);
    }
}
