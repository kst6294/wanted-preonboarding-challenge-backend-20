package wanted.preonboard.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wanted.preonboard.market.domain.dto.MemberDto;
import wanted.preonboard.market.domain.dto.ResponseBad;
import wanted.preonboard.market.domain.dto.ResponseOk;
import wanted.preonboard.market.domain.entity.Member;
import wanted.preonboard.market.message.MemberExceptionMessage;
import wanted.preonboard.market.service.Member.MemberService;

import java.util.Map;

@RestController
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerMember(@RequestBody MemberDto memberDto) {
        ResponseEntity<Map<String, Object>> response;
        try {
            String hashPwd = passwordEncoder.encode(memberDto.getPassword());
            memberDto.setPassword(hashPwd);
            if (memberService.insertMember(memberDto)) {
                response = new ResponseOk(MemberExceptionMessage.REGISTERED_SUCCESSFULLY.getMessage()).toResponse();
            } else {
                response = new ResponseBad(MemberExceptionMessage.ERROR.getMessage()).toResponse();
            }
        } catch (DataIntegrityViolationException e) {
            response = new ResponseBad(MemberExceptionMessage.USER_ALREADY_EXISTS.getMessage()).toResponse();
        }
        return response;
    }

    @RequestMapping("/user")
    public ResponseEntity<Map<String, Object>> getUserDetailsAfterLogin(Authentication authentication) {
        Member member = memberService.getMemberByEmail(authentication.getName());
        if (member != null) {
            return new ResponseOk(member).toResponse();
        } else {
            return null;
        }
    }
}
