package wanted.market.domain.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import wanted.market.domain.member.controller.dto.request.MemberJoinRequest;
import wanted.market.domain.member.controller.dto.request.MemberLoginRequest;
import wanted.market.domain.member.service.MemberService;
import wanted.market.domain.member.service.dto.response.MemberLoginResponse;
import wanted.market.global.dto.ApiResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/check-email")
    public ApiResponse<Boolean> join(@RequestParam("email") String email) {
        return ApiResponse.ok(memberService.checkEmail(email));
    }

    @PostMapping("/join")
    public ApiResponse<Boolean> join(@RequestBody MemberJoinRequest request) {
        return ApiResponse.ok(memberService.join(request.toServiceRequest()));
    }

    @PostMapping("/login")
    public ApiResponse<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {
        return ApiResponse.ok(memberService.login(request.toServiceRequest()));
    }

}
