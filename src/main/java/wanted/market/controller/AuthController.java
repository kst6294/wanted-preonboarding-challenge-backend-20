package wanted.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wanted.market.entity.User;
import wanted.market.repository.UserRepository;
import wanted.market.service.AuthService;
import wanted.market.util.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody Map<String, String> request) {
        try {
            String result = authService.registerUser(request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity createAuthenticationToken(@RequestBody Map<String, String> request) {
        try {
            String jwt = authService.createAuthenticationToken(request);
            return ResponseEntity.ok(Map.of("jwt", jwt));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity logoutUser() {
        return ResponseEntity.ok("로그아웃이 완료되었습니다.");
    }
}
