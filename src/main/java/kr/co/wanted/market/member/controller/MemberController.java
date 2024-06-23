package kr.co.wanted.market.member.controller;

import jakarta.validation.Valid;
import kr.co.wanted.market.common.global.dto.ApiResult;
import kr.co.wanted.market.common.global.enums.ErrorCode;
import kr.co.wanted.market.common.global.error.BizException;
import kr.co.wanted.market.member.dto.MemberJoin;
import kr.co.wanted.market.member.dto.MemberLogin;
import kr.co.wanted.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;


    @Override
    @PostMapping
    public ResponseEntity<Void> join(@RequestBody @Valid MemberJoin memberJoin) {

        try {

            memberService.joinMember(memberJoin);
        } catch (DataIntegrityViolationException e) {

            log.info("ID 중복: [{}]", memberJoin.id());
            throw new BizException(ErrorCode.MEMBER_ID_DUPLICATED);
        }

        return ResponseEntity.ok().build();
    }


    @Override
    @PostMapping("/login")
    public ResponseEntity<ApiResult<MemberLogin.Response>> login(@RequestBody @Valid MemberLogin.Request loginRequest) {

        return ResponseEntity.ok(
                new ApiResult<>(memberService.login(loginRequest))
        );
    }

}
