package com.wanted.market.member.ui;

import com.wanted.market.common.authentication.model.LoginMember;
import com.wanted.market.common.http.dto.response.ApiResponse;
import com.wanted.market.global.web.constance.SessionConst;
import com.wanted.market.member.exception.AuthenticationException;
import com.wanted.market.member.service.LoginService;
import com.wanted.market.member.service.UsernamePasswordLoginService;
import com.wanted.market.member.ui.dto.request.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final LoginService loginService;

    public AuthenticationController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        UsernamePasswordLoginService.UsernamePasswordLoginCommand cmd =
                new UsernamePasswordLoginService.UsernamePasswordLoginCommand(request.username(), request.password());
        LoginMember login = null;
        try {
            login = loginService.login(cmd);
            httpServletRequest.getSession(true).setAttribute(SessionConst.SESSION_KEY, login);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest()
                    .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.ok(new ApiResponse<>("success", null));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok(new ApiResponse<>("success", null));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ProblemDetail.forStatus(HttpStatus.NO_CONTENT));
    }
}
