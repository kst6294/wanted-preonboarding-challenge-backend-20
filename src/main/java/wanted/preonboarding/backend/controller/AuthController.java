package wanted.preonboarding.backend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.preonboarding.backend.auth.dto.LoginRequest;
import wanted.preonboarding.backend.auth.Session;
import wanted.preonboarding.backend.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public void signIn(@RequestBody final LoginRequest request, HttpSession session) {
        Long memberId = authService.signIn(request);
        session.setAttribute(Session.MEMBER, memberId);
    }
}
