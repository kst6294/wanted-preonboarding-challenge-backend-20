package wanted.Market.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.Market.domain.member.dto.MemberJoinRequest;
import wanted.Market.domain.member.service.MemberService;
import wanted.Market.global.common.ResponseDto;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDto<?>> join(@RequestBody MemberJoinRequest dto){
        memberService.join(dto.getUsername(), dto.getPassword());
        return ResponseEntity.status(200).body(ResponseDto.of("성공",null));
    }
}
